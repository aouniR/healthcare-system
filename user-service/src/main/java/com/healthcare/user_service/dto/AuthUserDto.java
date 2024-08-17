package com.healthcare.user_service.dto;

import java.util.UUID;
import java.time.LocalDateTime;
import com.healthcare.user_service.entity.Role;
import lombok.Data;

@Data
public class AuthUserDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Role role; 
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp; 
}
