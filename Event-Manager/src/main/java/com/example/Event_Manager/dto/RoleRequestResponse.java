package com.example.Event_Manager.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoleRequestResponse {
    private Long requestId;
    private String userName;
    private String userEmail; // Super Admin needs to know WHO asked
    private LocalDateTime requestedAt;

    public RoleRequestResponse(Long id, String name, String email, LocalDateTime requestedAt) {
        this.requestId = id;
        this.userName = name;
        this.userEmail = email;
        this.requestedAt = requestedAt;
    }
}