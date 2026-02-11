package com.example.Event_Manager.service;

import com.example.Event_Manager.dto.CreateEventRequest;
import com.example.Event_Manager.model.Event;
import com.example.Event_Manager.model.Role;
import com.example.Event_Manager.model.User;
import com.example.Event_Manager.repository.EventRepository;
import com.example.Event_Manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService; // <--- NEW INJECTION

    // --- CREATE ---
    public Event createEvent(CreateEventRequest request, String email){
        User host = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));

        // Enum check (assuming Role is an Enum, otherwise use .equals("HOST"))
        if(host.getRole() != Role.HOST || !host.isVerified()){
            throw new RuntimeException("Only verified hosts can create events");
        }

        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setPosterUrl(request.getPosterUrl());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());
        event.setVenue(request.getVenue());
        event.setCategory(request.getCategory());
        event.setMaxParticipants(request.getMaxParticipants());
        event.setRegistrationDeadline(request.getRegistrationDeadline());
        event.setHost(host);
        event.setCreatedAt(LocalDateTime.now());

        Event savedEvent = eventRepository.save(event);

        // Send Email
        emailService.sendEventCreationEmail(
                host.getEmail(),
                host.getName(),
                savedEvent.getTitle(),
                savedEvent.getEventDate().toString()
        );

        return savedEvent;
    }

    // --- READ ---
    public List<Event> getUpcomingEvents(){
        return eventRepository.findByEventDateAfter(LocalDateTime.now());
    }

    // --- UPDATE ---
    public Event updateEvent(Long eventId, CreateEventRequest request, String email) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User currentHost = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getHost().getId().equals(currentHost.getId())) {
            throw new RuntimeException("Unauthorized: You can only edit events created by you.");
        }

        // Apply updates
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setEventDate(request.getEventDate());
        event.setEventTime(request.getEventTime());
        event.setVenue(request.getVenue());
        event.setCategory(request.getCategory());
        event.setMaxParticipants(request.getMaxParticipants());
        event.setRegistrationDeadline(request.getRegistrationDeadline());
        if (request.getPosterUrl() != null) event.setPosterUrl(request.getPosterUrl());

        return eventRepository.save(event);
    }

    // --- DELETE ---
    public void deleteEvent(Long eventId, String email) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User currentHost = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!event.getHost().getId().equals(currentHost.getId())) {
            throw new RuntimeException("Unauthorized: You can only delete events created by you.");
        }

        eventRepository.delete(event);
    }
}