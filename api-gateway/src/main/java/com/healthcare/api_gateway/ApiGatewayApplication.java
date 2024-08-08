package com.healthcare.api_gateway;

import com.healthcare.api_gateway.filter.JwtAuthenticationFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

 @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder, JwtAuthenticationFilter jwtAuthenticationFilter) {
        return builder.routes()
            .route("user-service", r -> r.path("/api/users/**")
                .filters(f -> f.filters(jwtAuthenticationFilter)) 
                .uri("lb://USER-SERVICE"))
            .route("metamodel-service", r -> r.path("/api/metamodels/**")
                .filters(f -> f.filters(jwtAuthenticationFilter))
                .uri("lb://METAMODEL-SERVICE"))
            .route("medical-record-service", r -> r.path("/api/medicalrecords/**")
                .filters(f -> f.filters(jwtAuthenticationFilter))
                .uri("lb://MEDICAL-RECORD-SERVICE"))
            .route("medical-component-service", r -> r.path("/api/medicalcomponents/**")
                .filters(f -> f.filters(jwtAuthenticationFilter)) 
                .uri("lb://MEDICAL-COMPONENT-SERVICE"))
            .route("notification-service", r -> r.path("/api/notifications/**")
                .filters(f -> f.filters(jwtAuthenticationFilter)) 
                .uri("lb://NOTIFICATION-SERVICE"))
            .build();
    }
}
