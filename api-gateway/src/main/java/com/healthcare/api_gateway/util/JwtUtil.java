package com.healthcare.api_gateway.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;



@Component
public class JwtUtil {

    // @Value("${jwt.secret-key}")
    private final String secretKey="8bTjzD5Yl4I9jQ8M7G3wD1k+S3ZbqC+7e8A1aPqF2B4W=";


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }
}
