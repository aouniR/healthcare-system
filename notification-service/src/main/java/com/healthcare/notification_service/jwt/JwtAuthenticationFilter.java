package com.healthcare.notification_service.jwt;

import io.jsonwebtoken.Claims;
import org.springframework.lang.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);


    @Override
    protected void doFilterInternal(@NonNull jakarta.servlet.http.HttpServletRequest request,
            @NonNull jakarta.servlet.http.HttpServletResponse response, @NonNull jakarta.servlet.FilterChain filterChain)
            throws jakarta.servlet.ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader("Authorization");
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);
                if (StringUtils.hasText(token)) {
                    Claims claims = jwtUtil.getClaims(token);

                    if (claims != null) {
                        logger.info("Roles in JWT: {}", claims.get("roles"));
                        List<String> roles = jwtUtil.getRolesFromClaims(claims);
                        Collection<GrantedAuthority> authorities = roles.stream()
                                .map(SimpleGrantedAuthority::new) 
                                .collect(Collectors.toList());
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                    claims.getSubject(), null, authorities
                            );
                            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                        
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
        }
    }
}