package com.healthcare.user_service.kafka;

import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.dto.AuthUserDto;
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

    private final KafkaTemplate<String, AuthUserDto> kafkaTemplate;

    public void sendUserCreatedEvent(User user) {
        AuthUserDto userDTO = new AuthUserDto();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword("$$$$$");
        userDTO.setRole(user.getRole());
        userDTO.setCreationTimestamp(user.getCreationTimestamp());
        userDTO.setUpdateTimestamp(user.getUpdateTimestamp());
        kafkaTemplate.send(userEventsTopic, "USER_CREATED", userDTO);
    }

    public void sendUserDeletedEvent(UUID userId) {
        AuthUserDto userDTO = new AuthUserDto();
        userDTO.setId(userId);
        kafkaTemplate.send(userEventsTopic, "USER_DELETED", userDTO);
    }

    public void sendUserUpdatedEvent(User userUp) {
        AuthUserDto userDTO = new AuthUserDto();
        userDTO.setId(userUp.getId());
        userDTO.setUsername(userUp.getUsername());
        userDTO.setEmail(userUp.getEmail());
        userDTO.setPassword("$$$$$");
        userDTO.setRole(userUp.getRole());
        userDTO.setCreationTimestamp(userUp.getCreationTimestamp());
        userDTO.setUpdateTimestamp(userUp.getUpdateTimestamp());
        kafkaTemplate.send(userEventsTopic, "USER_UPDATED", userDTO);
    }
}

