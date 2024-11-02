package com.healthcare.authentication_service.service;

import com.healthcare.authentication_service.client.UserServiceClient;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserServiceClient userServiceClient;


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userServiceClient.getUserByUsername(username).getBody();
        assert user != null;
        return new CustomUserDetails(user);
    }
}