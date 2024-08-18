package com.healthcare.notification_service.service;

import java.util.List;
import java.util.UUID;
import com.healthcare.notification_service.dto.UserDto;
import com.healthcare.notification_service.entity.Notification;

public interface NotificationService {
    void saveNotification(String key, UserDto message);
    public void deleteNotification(UUID id);
    public List<Notification> getAllNotificationsByUserId(UUID id);
}

