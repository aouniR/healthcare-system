package com.healthcare.user_service.service;


import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.exception.NotFoundException;
import com.healthcare.user_service.repository.UserRepository;
import com.healthcare.user_service.request.RegisterRequest;
import com.healthcare.user_service.request.UserUpdateRequest;
import com.healthcare.user_service.kafka.UserEventProducer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public User updateUserById(UserUpdateRequest request) {
        User existingUser = userRepository.findById(request.getId())
            .orElseThrow(() -> new NotFoundException("User not found"));
    
        User originalUser = User.builder()
            .username(existingUser.getUsername())
            .password(existingUser.getPassword())
            .email(existingUser.getEmail())
            .role(existingUser.getRole())
            .build();
    
        modelMapper.map(request, existingUser);
        User updatedUser = userRepository.save(existingUser);
        userEventProducer.sendUserUpdatedEvent(originalUser, updatedUser);
        return updatedUser;
    }
    

    @Override
    public User saveUser(RegisterRequest request) {
        User toSave = User.builder()
            .username(request.getUsername())
            .password(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .role(request.getRole())
            .build();
    
        User savedUser = userRepository.save(toSave);
    
        if (savedUser != null) {
            userEventProducer.sendUserCreatedEvent(savedUser);
        } else {
            throw new RuntimeException("Failed to save user");
        }
        return savedUser;
    }
    

    @Override
    public void deleteUserById(UserUpdateRequest request) {
        User toDelete = userRepository.findById(request.getId())
            .orElseThrow(() -> new NotFoundException("User not found"));
        userRepository.delete(toDelete);
        userEventProducer.sendUserDeletedEvent(toDelete.getId());
    }  
}


