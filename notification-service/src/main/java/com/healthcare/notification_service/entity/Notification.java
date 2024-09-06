package com.healthcare.notification_service.entity;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Builder
@NoArgsConstructor 
@AllArgsConstructor
@Entity(name="notifications")
@Getter
@Setter
public class Notification implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID userId;
    private String key;
    private String message;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
}


    