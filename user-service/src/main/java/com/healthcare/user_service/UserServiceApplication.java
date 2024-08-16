package com.healthcare.user_service;

import com.healthcare.user_service.entity.User;
import com.healthcare.user_service.entity.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.healthcare.user_service.repository.UserRepository;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner{

	private final UserRepository userRepository;

    public UserServiceApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

    @Override
    public void run(String... args) {
        final String pass = "$G5$10$9OQz5zU4l0fBQ9Y63i1eMeJ1Gk1O1RdpGiLzQ97U5/N1kL08gVd16";
        var admin = User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(pass)
                .role(Role.ADMIN).build();
        if (userRepository.findByUsername("admin").isEmpty()) userRepository.save(admin);
    }
}



