package com.example.Apigateway.util;

import java.security.Key;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {


    public static final String SECRET = "d3780ec3d1cfaba271e0538d4fae686d8367e10155ee424691fbf191eabec53d";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }

    public String extractRolesFromToken(final String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
        System.out.println("Step2"+ claims);
        String authorities = (String) claims.get("roles"); // Assuming roles are stored as a List<String> in "roles" claim
        System.out.println("Step3"+ authorities);
        // Convert authorities to roles in the format "ROLE_ADMIN" or "ROLE_USER"

        return authorities;
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
