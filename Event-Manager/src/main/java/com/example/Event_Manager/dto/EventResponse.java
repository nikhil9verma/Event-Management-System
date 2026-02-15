package com.example.Event_Manager.dto;

import com.example.Event_Manager.model.Event;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime eventDate;
    private String venue;
    private String category;
    private Integer maxParticipants;
    private LocalDateTime registrationDeadline;
    private String posterUrl;
    private String hostName;

    public EventResponse(Event event) {
        this.id = event.getId();
        this.title = event.getTitle();
        this.description = event.getDescription();
        this.eventDate = event.getEventDate();
        this.venue = event.getVenue();
        this.category = event.getCategory();
        this.maxParticipants = event.getMaxParticipants();
        this.registrationDeadline = event.getRegistrationDeadline();
        this.posterUrl = event.getPosterUrl();
        this.hostName = event.getHost().getName();
    }
}
