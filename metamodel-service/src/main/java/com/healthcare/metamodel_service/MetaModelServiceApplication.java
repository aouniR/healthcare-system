package com.healthcare.metamodel_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MetaModelServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetaModelServiceApplication.class, args);
	}

}
