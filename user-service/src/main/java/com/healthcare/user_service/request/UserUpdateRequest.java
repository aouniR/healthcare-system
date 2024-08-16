package com.healthcare.user_service.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRequest {
    @NotNull(message = "Id is required")
    private UUID id;
    private String username;
    private String password;
}