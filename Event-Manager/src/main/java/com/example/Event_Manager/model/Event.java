package com.example.Event_Manager.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="events")
@Data
public class Event {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)

    private Long Id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable=false)
    private LocalDateTime eventDate;

    @Column(nullable=false)
    private LocalDateTime eventTime;

    @Column(nullable=false)
    private String venue;

    @Column(nullable = false)
    private String category;

    @Column(nullable=false)
    private int maxParticipants;

    @Column(nullable= false)
    private LocalDateTime registrationDeadline;

    @Column(nullable = true)
    private String posterUrl;

    @ManyToOne
    @JoinColumn(name="host_id",nullable = false)
    private User host;

    @Column (nullable =false)
    private LocalDateTime createdAt;

}
