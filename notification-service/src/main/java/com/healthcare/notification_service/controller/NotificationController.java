package com.healthcare.notification_service.controller;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.service.NotificationServiceImpl;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationServiceImpl notificationServiceImpl;

    @GetMapping("/getAllByUserId/{userId}")
    public ResponseEntity<List<Notification>> getAllByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(notificationServiceImpl.getAllNotificationsByUserId(userId));
    }
}
