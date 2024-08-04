package com.healthcare.notification_service.controller;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.service.NotificationService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @Autowired
    public NotificationController(NotificationService notificationService){
        this.notificationService=notificationService;
    }

    @GetMapping
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable UUID id) {
        Notification metamodel = notificationService.getNotificationById(id);
        return metamodel != null ? ResponseEntity.ok(metamodel) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Notification createNotification(@RequestBody Notification metamodel) {
        return notificationService.saveNotification(metamodel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable UUID id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }
}
