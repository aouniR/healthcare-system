package com.healthcare.notification_service.service;

import java.util.List;
import java.util.UUID;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.request.SendNotificationRequest;

public interface NotificationService {
    Notification getNotificationById(UUID id);
    void saveNotification(SendNotificationRequest request);
    void deleteNotification(UUID id);
    List<Notification> getAllNotificationsByUserId(UUID id);
}