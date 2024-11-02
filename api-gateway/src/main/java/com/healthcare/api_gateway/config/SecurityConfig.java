package com.healthcare.api_gateway.config;

import com.healthcare.api_gateway.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig implements WebFluxConfigurer {

    private final JwtAuthenticationFilter filter;

    @Bean 
    public RouteLocator routes(RouteLocatorBuilder builder){
        return builder.routes()
        .route("authentication-service", r -> r.path("/auth/**")
            .filters(f -> f.filters(filter))
            .uri("lb://authentication-service")) 
        .route("user-service", r -> r.path("/users/**")
            .filters(f -> f.filters(filter)) 
            .uri("lb://user-service"))
        .route("metamodel-service", r -> r.path("/metamodels/**")
            .filters(f -> f.filters(filter))
            .uri("lb://metamodel-service"))
        .route("medicalrecord-service", r -> r.path("/medicalrecords/**")
            .filters(f -> f.filters(filter))
            .uri("lb://medicalrecord-service"))
        .route("notification-service", r -> r.path("/notifications/**")
            .filters(f -> f.filters(filter)) 
            .uri("lb://notification-service"))
        .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource source = new org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
    
        return new CorsWebFilter(source);
    }
    
}
