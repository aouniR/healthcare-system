package com.healthcare.notification_service.service;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.dto.UserDto;
import com.healthcare.notification_service.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;

    @Override
    public void saveNotification(String key, UserDto message){
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJson;
        try {
            messageJson = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize UserDto", e);
        }

        var notification = Notification.builder()
                                       .id(UUID.randomUUID())
                                       .userId(message.getId())
                                       .key(key)
                                       .message(messageJson)
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

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreationTimestampDesc();
    }
}

