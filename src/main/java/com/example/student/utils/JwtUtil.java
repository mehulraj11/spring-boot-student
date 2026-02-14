package com.example.student.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECRET = "hello_helloo_bheem_ki_shakti_dhoom_machaye";
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String email) {
        long EXPIRATION_TIME = 1000 * 60 * 120;
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }
//        this will store the payload of the JWT
//    used before security context holder works for jwt before complete authentication
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) //verifies token signature
                .build()
                .parseClaimsJws(token)//checks expiration automatically
                .getBody();//extract payload and return it as claims object
    }

    public boolean validateToken(String email, UserDetails usersDetails, String token) {
        return email.equals(usersDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}