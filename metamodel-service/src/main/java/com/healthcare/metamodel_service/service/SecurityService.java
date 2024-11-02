package com.healthcare.metamodel_service.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityService {
    public boolean isOwner(UUID userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserId = (String) authentication.getPrincipal();  
    
        return currentUserId.equals(userId.toString());  
    }
    
}
