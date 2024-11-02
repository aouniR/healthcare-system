package com.healthcare.notification_service.listeners;

import com.healthcare.notification_service.service.NotificationServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {

    private final NotificationServiceImpl notificationServiceImpl;

    @KafkaListener(topics = "${spring.kafka.topic.user-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleUserEvent(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload String message) {
        log.info("Received message with key: {} and value: {}", key, message);
        if ("USER_CREATED".equals(key) || "USER_DELETED".equals(key) || "USER_UPDATED".equals(key)) {
            notificationServiceImpl.saveUserNotification(key, message);
        }
    }

    @KafkaListener(topics = "${spring.kafka.topic.metamodel-events}", groupId = "${spring.kafka.consumer.group-id}")
    public void handleMetamodelEvent(@Header(KafkaHeaders.RECEIVED_KEY) String key, @Payload String message) {
        log.info("Received metamodel event with key: {} and value: {}", key, message);
        if ("METAMODEL_CREATED".equals(key) || "METAMODEL_UPDATED".equals(key) || "METAMODEL_DELETED".equals(key)) {
            notificationServiceImpl.saveMetalModelNotification(key, message);
        }
    }
}