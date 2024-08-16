package com.healthcare.user_service;

import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.healthcare.user_service.repository.UserRepository;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import lombok.AllArgsConstructor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@AllArgsConstructor
public class UserServiceApplication implements CommandLineRunner{

	private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

    @Override
    public void run(String... args) {
        final String pass = "admin";
        var admin = User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode(pass))
                .role(Role.ADMIN).build();
        if (userRepository.findByUsername("admin").isEmpty()) userRepository.save(admin);
    }
}



