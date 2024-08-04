package com.healthcare.notification_service.service;

import java.util.List;
import java.util.UUID;

import com.healthcare.notification_service.entity.Notification;

public interface NotificationService {
    List<Notification> getAllNotifications();
    Notification getNotificationById(UUID id);
    Notification saveNotification(Notification Notification);
    void deleteNotification(UUID id);
}