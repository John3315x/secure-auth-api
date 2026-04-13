package com.nanakusa.secureauthapi.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTc3NTIyNjYyMywiZXhwIjoxNzc1MjI4NDIzfQ.PeD5N2MiVf7KQwulyYzkTO0yIh6KHsvqPU9X5-3YpPE";

    private static final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
    //private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10)) // 30 min
                .signWith(key)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // username

        } catch (JwtException e) {
            return null;
        }
    }
}
