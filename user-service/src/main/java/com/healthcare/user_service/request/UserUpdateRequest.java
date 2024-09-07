package com.healthcare.user_service.request;
import lombok.Data;

@Data
public class UserUpdateRequest {
    private String username;
    private String password;
}