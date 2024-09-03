package com.healthcare.user_service.request;

import com.healthcare.user_service.entity.Role;
import lombok.Data;

@Data
public class ValidRegisterRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}