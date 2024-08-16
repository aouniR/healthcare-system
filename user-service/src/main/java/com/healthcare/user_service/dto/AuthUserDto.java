package com.healthcare.user_service.dto;

import java.util.UUID;

import com.healthcare.user_service.entity.Role;
import lombok.Data;

@Data
public class AuthUserDto {
    private UUID id;
    private String username;
    private String password;
    private Role role;
}
