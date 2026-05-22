package com.example.student.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${secret_key}")
    private  String SECRET;
    private  SecretKey key;
    @PostConstruct
    public void init(){
        key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String email, String role) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
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
    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }
    public String extractRole(String token) {
        return extractClaims(token).get("role", String.class);
    }
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}