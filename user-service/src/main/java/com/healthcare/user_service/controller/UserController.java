package com.healthcare.user_service.controller;

import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.service.UserServiceImpl;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl){
        this.userServiceImpl=userServiceImpl;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userServiceImpl.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        User user = userServiceImpl.getUserById(id);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userServiceImpl.saveUser(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

