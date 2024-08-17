package com.healthcare.notification_service.service;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.request.SendNotificationRequest;
import com.healthcare.notification_service.repository.NotificationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;


    @Override
    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public void saveNotification(SendNotificationRequest request) {
        var notification = Notification.builder()
                                        .id(UUID.randomUUID())
                                        .userId(request.getUserId())
                                        .message(request.getMessage())
                                        .build();
        notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(UUID id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> getAllNotificationsByUserId(UUID id) {
        return notificationRepository.findAllByUserIdOrderByCreationTimestampDesc(id);
    }
}

