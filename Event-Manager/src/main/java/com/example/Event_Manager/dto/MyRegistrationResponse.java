package com.example.Event_Manager.dto;

import com.example.Event_Manager.model.Event;
import com.example.Event_Manager.model.RegistrationStatus;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MyRegistrationResponse {
    private Long registrationId;
    private RegistrationStatus status;
    private LocalDateTime registeredAt;

    // Flattened Event Details (Safe for Frontend)
    private Long eventId;
    private String eventTitle;
    private LocalDateTime eventDate;
    private String eventVenue;       // <--- Corrected field name
    private String eventPosterUrl;   // Useful for the dashboard UI
    private String hostName;         // Only sending the name, not the full User object

    public MyRegistrationResponse(Long id, RegistrationStatus status, LocalDateTime registeredAt, Event event) {
        this.registrationId = id;
        this.status = status;
        this.registeredAt = registeredAt;

        // Map fields manually
        this.eventId = event.getId();
        this.eventTitle = event.getTitle();
        this.eventDate = event.getEventDate();
        this.eventVenue = event.getVenue();
        this.eventPosterUrl = event.getPosterUrl();
        this.hostName = event.getHost().getName(); // Assuming User has a .getName() method
    }
}