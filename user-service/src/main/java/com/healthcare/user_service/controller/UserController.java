package com.healthcare.user_service.controller;

import com.healthcare.user_service.request.UserUpdateRequest;
import com.healthcare.user_service.dto.UserDto;
import com.healthcare.user_service.service.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping("/users/getMyInfo/{id}")
    @PreAuthorize("@securityService.isOwner(#userId)")
    public ResponseEntity<UserDto> getMyInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.getUserById(id), UserDto.class));
    }

    @PutMapping("/users/update/{id}")
    @PreAuthorize("@securityService.isOwner(#userId)")
    public ResponseEntity<UserDto> updateUserById(@PathVariable UUID id, @Valid @RequestPart UserUpdateRequest request) {
        return ResponseEntity.ok(modelMapper.map(userServiceImpl.updateUserById(id,request), UserDto.class));
    }
}
