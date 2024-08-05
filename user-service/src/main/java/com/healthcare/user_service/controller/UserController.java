package com.healthcare.user_service.controller;

import com.healthcare.user_service.service.UserServiceImpl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @PostMapping("/verify")
    public ResponseEntity<Boolean> verifyUser(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");
        boolean isValid = userServiceImpl.verifyUserCredentials(email, password);
        return ResponseEntity.ok(isValid);
    }
}
