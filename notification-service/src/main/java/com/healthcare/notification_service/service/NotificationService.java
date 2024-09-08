package com.healthcare.notification_service.service;

import java.util.List;
import java.util.UUID;
import com.healthcare.notification_service.entity.Notification;

public interface NotificationService {
    void saveUserNotification(String key, String message);
    void saveMetalModelNotification(String key, String message);
    public void deleteNotification(UUID id);
    public List<Notification> getAllNotificationsByUserId(UUID id);
    public List<Notification> getAllNotifications();
}

