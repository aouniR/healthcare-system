package com.healthcare.notification_service.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.notification_service.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>{
}

