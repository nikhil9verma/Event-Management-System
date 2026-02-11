package com.example.Event_Manager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class CreateEventRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDateTime eventDate;

    @NotNull
    private LocalDateTime eventTime;

    @NotBlank
    private String venue;

    @NotBlank
    private String category;

    @NotNull
    private Integer maxParticipants;

    @NotNull
    private LocalDateTime registrationDeadline;

    private String posterUrl;
}
