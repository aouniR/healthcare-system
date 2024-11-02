package com.healthcare.user_service.controller;

import com.healthcare.user_service.request.RegisterRequest;
import com.healthcare.user_service.request.UserUpdateRequest;
import com.healthcare.user_service.dto.AuthUserDto;
import com.healthcare.user_service.dto.UserDto;
import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AdminUserController {

    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;


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

    @GetMapping("/internal/users/getUserByUsername/{username}")
    public ResponseEntity<AuthUserDto> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.getUserByUsername(username), AuthUserDto.class));
    }

    @PostMapping("/internal/users/save")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.saveUser(request), UserDto.class));
    }
    @PutMapping("/users/updateByAdmin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUserById(@PathVariable UUID id, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.ok(userServiceImpl.updateUserById(id,request));
    }

    @DeleteMapping("/users/deleteUserById/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
        userServiceImpl.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }


}
