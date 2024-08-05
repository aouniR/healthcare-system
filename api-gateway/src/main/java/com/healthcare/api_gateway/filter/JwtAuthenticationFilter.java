package com.healthcare.api_gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AuthenticationWebFilter implements GatewayFilter{

    public JwtAuthenticationFilter(ServerAuthenticationConverter authenticationConverter) {
        super((ReactiveAuthenticationManager) authenticationConverter);
        this.setAuthenticationSuccessHandler((exchange, authentication) -> Mono.empty());
        this.setAuthenticationFailureHandler((exchange, exception) -> Mono.error(exception));
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }
}
