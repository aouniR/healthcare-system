package com.healthcare.notification_service.service;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.dto.MetaModelDto;
import com.healthcare.notification_service.dto.UserDto;
import com.healthcare.notification_service.repository.NotificationRepository;

import jakarta.annotation.PostConstruct;
import jakarta.ws.rs.NotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;
    private static final Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);
    private ObjectMapper objectMapper;
    
    @PostConstruct
    public void setUp() {
        objectMapper = new ObjectMapper(); 
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void saveUserNotification(String key, String message){

        try {
            UserDto user = objectMapper.readValue(message, UserDto.class);
            var notification = Notification.builder()
                                            .id(UUID.randomUUID())
                                            .userId(user.getId())
                                            .key(key)
                                            .message(message)
                                            .build();
            notificationRepository.save(notification);
            log.info("Notification saved: {}", notification);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save notification", e);
        }
    }

    @Override
    public void saveMetalModelNotification(String key, String message){

        try {
            MetaModelDto metaModel = objectMapper.readValue(message, MetaModelDto.class);
            var notification = Notification.builder()
                                            .id(UUID.randomUUID())
                                            .userId(metaModel.getCreatorId())
                                            .key(key)
                                            .message(message)
                                            .build();
            notificationRepository.save(notification);
            log.info("Notification saved: {}", notification);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save notification", e);
        }
    }

    @Override
    public void deleteNotification(UUID id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAllByOrderByCreationTimestampDesc();
    }

    @Override
    public Notification getNotificationById(UUID id){
        return notificationRepository.findById(id).orElseThrow(() -> new NotFoundException("Notification not found"));
    }

    @Override
    public void deleteNotificationById(UUID id) {
        Notification toDelete = notificationRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Notificaiton not found"));
        notificationRepository.delete(toDelete);
    }  
}

