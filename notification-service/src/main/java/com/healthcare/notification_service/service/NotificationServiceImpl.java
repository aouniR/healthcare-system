package com.healthcare.notification_service.service;

import com.healthcare.notification_service.entity.Notification;
import com.healthcare.notification_service.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {
    
    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationServiceImpl(NotificationRepository notificationRepository){
        this.notificationRepository=notificationRepository;
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    @Override
    public Notification getNotificationById(UUID id) {
        return notificationRepository.findById(id).orElse(null);
    }

    @Override
    public Notification saveNotification(Notification metamodel) {
        return notificationRepository.save(metamodel);
    }

    @Override
    public void deleteNotification(UUID id) {
        notificationRepository.deleteById(id);
    }
}

