package com.healthcare.notification_service.entity;

import lombok.*;
import java.util.UUID;
import jakarta.persistence.*;

@Data
@Table
@Builder
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="notificationID")
    private UUID id;

    @Column(name="message")
    private String message;
    @Column(name="destinataire")
    private String destinataire;  
}


    