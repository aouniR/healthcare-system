package com.healthcare.medicalrecord_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableMongoAuditing
public class MetaModelServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MetaModelServiceApplication.class, args);
	}

}
