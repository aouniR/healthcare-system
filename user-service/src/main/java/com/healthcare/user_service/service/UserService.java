package com.healthcare.user_service.service;


import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.request.RegisterRequest;
import com.healthcare.user_service.request.UserUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User getUserByUsername(String username);
    User updateUserById(UUID id,UserUpdateRequest request);
    User saveUser(RegisterRequest request);
    void deleteUserById(UUID id);
}

