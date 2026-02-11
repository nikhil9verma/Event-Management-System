package com.example.Event_Manager.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterEventRequest {

    @NotNull
    private Long eventId;
}
