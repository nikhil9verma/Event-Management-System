package com.example.Event_Manager.controller;

import com.example.Event_Manager.dto.CreateEventRequest;
import com.example.Event_Manager.model.Event;
import com.example.Event_Manager.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    private final EventService eventService;

    // Constructor Injection
    public EventController(EventService eventService){
        this.eventService = eventService;
    }

    // --- PUBLIC / SHARED ENDPOINTS ---

    // Any logged-in user (Student or Host) can view events
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public ResponseEntity<List<Event>> getEvents(){
        return ResponseEntity.ok(eventService.getUpcomingEvents());
    }

    // --- HOST ONLY ENDPOINTS ---

    // 1. Create Event
    @PreAuthorize("hasRole('HOST')")
    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody CreateEventRequest request, Authentication authentication){
        // We extract the email from the token to assign the 'Host' to this event
        String email = authentication.getName();
        Event event = eventService.createEvent(request, email);
        return new ResponseEntity<>(event, HttpStatus.CREATED);
    }

    // 2. Edit Event
    @PreAuthorize("hasRole('HOST')")
    @PutMapping("/{eventId}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long eventId,
                                             @Valid @RequestBody CreateEventRequest request,
                                             Authentication authentication) {
        // We pass the email to the Service so it can verify OWNERSHIP
        // (i.e., ensure this Host actually owns this event before updating)
        String email = authentication.getName();
        Event updatedEvent = eventService.updateEvent(eventId, request, email);

        return ResponseEntity.ok(updatedEvent);
    }

    // 3. Delete Event
    @PreAuthorize("hasRole('HOST')")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId, Authentication authentication) {
        // We pass the email to Service to verify OWNERSHIP before deleting
        String email = authentication.getName();
        eventService.deleteEvent(eventId, email);

        return ResponseEntity.ok("Event deleted successfully");
    }
}