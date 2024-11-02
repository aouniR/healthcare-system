package com.healthcare.authentication_service.service;

import com.healthcare.authentication_service.jwt.JwtUtil;
import com.healthcare.authentication_service.client.UserServiceClient;
import com.healthcare.authentication_service.dto.RegisterDto;
import com.healthcare.authentication_service.dto.UserDto;
import com.healthcare.authentication_service.dto.TokenDto;
import com.healthcare.authentication_service.exception.WrongCredentialsException;
import com.healthcare.authentication_service.request.LoginRequest;
import com.healthcare.authentication_service.request.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserServiceClient userServiceClient;
    private final JwtUtil JwtUtil;
    private final PasswordEncoder passwordEncoder;

    public TokenDto login(LoginRequest request) {
        try {
            ResponseEntity<UserDto> userResponse = userServiceClient.getUserByUsername(request.getUsername());
            UserDto user = userResponse.getBody();
            
            if (user == null) {
                throw new WrongCredentialsException("User not found");
            }

            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                return TokenDto.builder()
                        .token(JwtUtil.generateToken(request.getUsername()))
                        .build();
            } else {
                throw new WrongCredentialsException("Wrong credentials");
            }
        } catch (Exception e) {
            throw new WrongCredentialsException("Authentication failed");
        }
    }
    
    public RegisterDto register(RegisterRequest request) {
        return userServiceClient.save(request).getBody();
    }
}
