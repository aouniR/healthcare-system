package com.healthcare.notification_service.controller;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.service.NotificationServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationServiceImpl;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Notification> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(notificationServiceImpl.getNotificationById(id));
    }

    @GetMapping("/getAll")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Notification>> getAll() {
        return ResponseEntity.ok(notificationServiceImpl.getAllNotifications());
    }

    @DeleteMapping("/deleteNotificationById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNotificationById(@PathVariable UUID id) {
        notificationServiceImpl.deleteNotificationById(id);
        return ResponseEntity.noContent().build();
    }
}
