package com.healthcare.user_service.service;


import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.entity.Role;
import com.healthcare.user_service.exception.NotFoundException;
import com.healthcare.user_service.repository.UserRepository;
import com.healthcare.user_service.request.UserUpdateRequest;
import com.healthcare.user_service.request.RegisterRequest;
import com.healthcare.user_service.kafka.UserEventProducer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private UserEventProducer userEventProducer;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User updateUserById(UUID id, UserUpdateRequest request) {
        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
        modelMapper.map(request, existingUser);
        User updatedUser = userRepository.save(existingUser);
        userEventProducer.sendUserUpdatedEvent(updatedUser);
        return updatedUser;
    }

    private Role convertToRoleEnum(String roleStr) {
        try {
            return Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role value: " + roleStr);
        }
    }

    @Override
    public User saveUser(RegisterRequest request) {
        Role role = convertToRoleEnum(request.getRole());
        User toSave = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .role(role)
            .build();
    
        User savedUser = userRepository.save(toSave);
    
        userEventProducer.sendUserCreatedEvent(savedUser);
        return savedUser;
    }
    

    @Override
    public void deleteUserById(UUID id) {
        User toDelete = userRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(toDelete);
        userEventProducer.sendUserDeletedEvent(toDelete.getId());
    }  
}


