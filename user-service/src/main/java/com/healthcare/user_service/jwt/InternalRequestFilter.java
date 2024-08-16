package com.healthcare.user_service.jwt;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class InternalRequestFilter extends OncePerRequestFilter {
    private static final String INTERNAL_SERVICE_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, 
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("Internal-Service-Token");
        if ("/internal/**".equals(request.getRequestURI()) && INTERNAL_SERVICE_TOKEN.equals(token)) {
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("internal", null, Collections.emptyList()));
        }
        filterChain.doFilter(request, response);
    }
}
