package com.epam.workloads.service.impl;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.SecretKey;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author jdmon on 12/01/2026
 * @project workloads-service
 */
class JwtServiceImplTest {

    private JwtServiceImpl jwtService;
    private SecretKey secretKey;

    @BeforeEach
    void setUp() {
        String secret = "mysecretkeymysecretkeymysecretkey12";
        jwtService = new JwtServiceImpl(secret);
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    private String generateToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60000)) // 1 min
                .signWith(secretKey)
                .compact();
    }

    @Test
    void isTokenValidShouldReturnTrueForValidToken() {
        String token = generateToken("jdoe");
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void isTokenValidShouldReturnFalseForInvalidToken() {
        String invalidToken = "not-a-jwt";

        assertFalse(jwtService.isTokenValid(invalidToken));
    }

    @Test
    void getUsernameShouldReturnSubject() {
        String token = generateToken("jdoe");

        String username = jwtService.getUsername(token);

        assertEquals("jdoe", username);
    }

}