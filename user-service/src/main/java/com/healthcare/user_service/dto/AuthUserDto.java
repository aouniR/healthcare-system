package com.healthcare.user_service.dto;

import com.healthcare.user_service.entity.Role;
import java.util.UUID;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserDto {
    private UUID id;
    private String username;
    private String email;
    private String password;
    private Role role; 
    private LocalDateTime creationTimestamp;
    private LocalDateTime updateTimestamp; 
}
