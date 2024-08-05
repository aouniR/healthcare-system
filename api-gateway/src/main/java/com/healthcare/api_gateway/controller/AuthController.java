package com.healthcare.api_gateway.controller;

import com.healthcare.api_gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${user-service.url}")
    private String userServiceUrl;

    @SuppressWarnings("null")
    @PostMapping("/login")
    public ResponseEntity<?> login(Map<String, String> credentials) {
        String verificationUrl = "http://USER-SERVICE/api/users/verify";
        ResponseEntity<Boolean> response = restTemplate.postForEntity(verificationUrl, credentials, Boolean.class);
    
        if (response.getBody() != null && response.getBody()) {
            // Generate JWT token
            String token = jwtUtil.generateToken(credentials.get("email"));
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
