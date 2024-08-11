package com.healthcare.notification_service.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Data
@Builder
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="notificationID")
    private UUID id;
    private UUID userId;
    private String message;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
}


    