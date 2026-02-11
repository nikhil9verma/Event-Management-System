package com.example.Event_Manager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

// com.example.Event_Manager.model.RoleRequest
@Entity
@Table(name = "role_requests")
@Data
public class RoleRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // <--- DANGER: Don't expose this directly to API

    private LocalDateTime requestedAt;
}
