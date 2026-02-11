package com.example.Event_Manager.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private  String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);

        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    public String generateToken(String email,String role){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime()+expiration);

        return Jwts.builder()
                .setSubject(email)
                .claim("role",role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
