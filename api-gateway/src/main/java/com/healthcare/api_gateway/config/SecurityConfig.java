package com.healthcare.api_gateway.config;

import com.healthcare.api_gateway.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter filter;

    @Bean 
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
        .route("authentication-service", r -> r.path("/api/v1/auth/**")
            .filters(f -> f.filters(filter))
            .uri("lb://authentication-service")) 
        .route("user-service", r -> r.path("/api/v1/users/**")
            .filters(f -> f.filters(filter)) 
            .uri("lb://user-service"))
        .route("metamodel-service", r -> r.path("/api/v1/metamodels/**")
            .filters(f -> f.filters(filter))
            .uri("lb://metamodel-service"))
        .route("medical-record-service", r -> r.path("/api/v1/medicalrecords/**")
            .filters(f -> f.filters(filter))
            .uri("lb://medical-record-service"))
        .route("medical-component-service", r -> r.path("/api/v1/medicalcomponents/**")
            .filters(f -> f.filters(filter)) 
            .uri("lb://medical-component-service"))
        .route("notification-service", r -> r.path("/api/v1/notifications/**")
            .filters(f -> f.filters(filter)) 
            .uri("lb://notification-service"))
        .build();
    }

}
