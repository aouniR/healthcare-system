package com.healthcare.notification_service.dto;

import java.util.UUID;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private string role; 
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp; 
}
