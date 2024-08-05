package com.healthcare.user_service.controller;

import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.entity.TypeProfil;
import com.healthcare.user_service.service.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AdminUserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
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
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getTypeProfil() == TypeProfil.ADMIN || 
            user.getTypeProfil() == TypeProfil.PROFESSIONNELDESANTE || 
            user.getTypeProfil() == TypeProfil.AGENTADMINISTRATIF) {
            return ResponseEntity.ok(userServiceImpl.saveUser(user));
        } else {
            return ResponseEntity.status(403).build(); // Forbidden if trying to create a role other than the allowed ones
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody User user) {
        User updatedUser = userServiceImpl.updateUser(id, user);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userServiceImpl.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
