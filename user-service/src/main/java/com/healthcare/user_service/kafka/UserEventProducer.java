package com.healthcare.user_service.kafka;

import com.healthcare.user_service.entity.User;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserEventProducer {

    @Value("${kafka.topic.user-events}")
    private String userEventsTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendUserCreatedEvent(User user) {
        kafkaTemplate.send(userEventsTopic, "USER_CREATED", user);
    }

    public void sendUserDeletedEvent(UUID userId) {
        kafkaTemplate.send(userEventsTopic, "USER_DELETED", userId);
    }

    public void sendUserUpdatedEvent(User userPrev, User userUp) {
        kafkaTemplate.send(userEventsTopic, "USER_UPDATED", userPrev, userUp);
    }
}

