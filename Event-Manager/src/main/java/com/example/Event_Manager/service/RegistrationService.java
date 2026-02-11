package com.example.Event_Manager.service;

import com.example.Event_Manager.dto.MyRegistrationResponse;
import com.example.Event_Manager.model.Event;
import com.example.Event_Manager.model.Registration;
import com.example.Event_Manager.model.RegistrationStatus;
import com.example.Event_Manager.model.User;
import com.example.Event_Manager.repository.EventRepository;
import com.example.Event_Manager.repository.RegistrationRepository;
import com.example.Event_Manager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EmailService emailService; // <--- NEW INJECTION



    public void registerForEvent(Long eventId, String email) {
        System.out.println("Hitted register for event");
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        // Checks
        if (event.getHost().getId().equals(user.getId())) {
            throw new RuntimeException("Host cannot register for their own event");
        }
        if (LocalDateTime.now().isAfter(event.getRegistrationDeadline())) {
            throw new RuntimeException("Registration deadline has passed");
        }
        if (registrationRepository.existsByStudentAndEvent(user, event)) {
            throw new RuntimeException("Already registered for this event");
        }
        long currentCount = registrationRepository.countByEvent(event);
        if (currentCount >= event.getMaxParticipants()) {
            throw new RuntimeException("Event is full");
        }

        // Save Registration
        Registration registration = new Registration();
        registration.setStudent(user);
        registration.setEvent(event);
        registration.setStatus(RegistrationStatus.REGISTERED);
        registration.setRegisteredAt(LocalDateTime.now());

        registrationRepository.save(registration);

        // Send Email
        System.out.println("Reached email service");
        emailService.sendRegistrationEmail(
                user.getEmail(),
                user.getName(),
                event.getTitle(),
                event.getEventDate().toString()
        );
    }

    public List<MyRegistrationResponse> getMyRegistrations(String email){
        User student = userRepository.findByEmail(email)
                .orElseThrow(()-> new RuntimeException("User not found"));
        List<Registration> registrations = registrationRepository.findByStudent(student);

        // Return Safe DTO
        return registrations.stream()
                .map(reg-> new MyRegistrationResponse(
                        reg.getId(),
                        reg.getStatus(),
                        reg.getRegisteredAt(),
                        reg.getEvent() // Ensure your DTO constructor handles this safely!
                ))
                .collect(Collectors.toList());
    }
}