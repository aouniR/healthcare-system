package com.healthcare.authentication_service.request;
import com.healthcare.authentication_service.enums.Role;
import lombok.Getter;

@Getter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Role role;
}
