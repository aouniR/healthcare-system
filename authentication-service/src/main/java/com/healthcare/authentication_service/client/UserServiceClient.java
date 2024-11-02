package com.healthcare.authentication_service.client;

import com.healthcare.authentication_service.config.FeignConfig;
import com.healthcare.authentication_service.dto.RegisterDto;
import com.healthcare.authentication_service.dto.UserDto;
import com.healthcare.authentication_service.request.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", path = "internal/users", configuration = FeignConfig.class)
public interface UserServiceClient {
    @PostMapping("/save")
    ResponseEntity<RegisterDto> save(@RequestBody RegisterRequest request);

    @GetMapping("/getUserByUsername/{username}")
    ResponseEntity<UserDto> getUserByUsername(@PathVariable String username);
}