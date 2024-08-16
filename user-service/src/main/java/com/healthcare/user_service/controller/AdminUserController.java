package com.healthcare.user_service.controller;

import com.healthcare.user_service.request.RegisterRequest;
import com.healthcare.user_service.request.UserUpdateRequest;
import com.healthcare.user_service.dto.AuthUserDto;
import com.healthcare.user_service.dto.UserDto;
import com.healthcare.user_service.service.UserServiceImpl;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
public class AdminUserController {

    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    @Autowired
    public AdminUserController(UserServiceImpl userServiceImpl, ModelMapper modelMapper) {
        this.userServiceImpl = userServiceImpl;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/getAllUsers")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userServiceImpl.getAllUsers().stream()
        .map(user -> modelMapper.map(user, UserDto.class)).toList());
    }

    @GetMapping("/users/getUserById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.getUserById(id), UserDto.class));
    }

    @GetMapping("/users/getUserByEmail/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.getUserByEmail(email), UserDto.class));
    }

    @GetMapping("/internal/users/getUserByUsername/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.getUserByUsername(username), AuthUserDto.class));
    }

    @PutMapping("/users/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUserById(@Valid @RequestPart UserUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.updateUserById(request), UserDto.class));
    }

    @PostMapping("/internal/users/save")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.saveUser(request), UserDto.class));
    }


    @DeleteMapping("/users/deleteUserById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserById(@Valid @RequestPart UserUpdateRequest request) {
        userServiceImpl.deleteUserById(request);
        return ResponseEntity.noContent().build();
    }
}
