package com.healthcare.user_service.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotBlank(message = "Id is required")
    private UUID id;
    private String username;
    private String password;
}