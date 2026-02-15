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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleRequestStatus status;

    @Column(nullable = false)
    private LocalDateTime requestedAt;

    private LocalDateTime reviewedAt;

    @ManyToOne
    @JoinColumn(name = "reviewed_by")
    private User reviewedBy; // SUPER_ADMIN

    private String rejectionReason;
}

