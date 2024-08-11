package com.healthcare.notification_service.listeners;

import com.healthcare.notification_service.request.SendNotificationRequest;
import com.healthcare.notification_service.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationListener {
    private final NotificationService notificationService;

    @KafkaListener(topics = {"${spring.kafka.topic.name}"}, groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final SendNotificationRequest request) {
        log.info("Consumed message: {}", request.toString());
        notificationService.saveNotification(request);
    }
}