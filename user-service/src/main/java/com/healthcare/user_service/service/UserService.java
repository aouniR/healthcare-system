package com.healthcare.user_service.service;


import com.healthcare.user_service.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(UUID id);
    User saveUser(User User);
    void deleteUser(UUID id);
}

