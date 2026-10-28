package com.example.violationmanagementsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.function.Function;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration-ms:3600000}")
    private long expirationMs;

    public String generateToken(Long userId) {

        Map<String, Object> claims = new HashMap<>();
        Date now = new Date();

        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(userId.toString())
                .issuer("VMS")
                .audience().add("VMS").and()
                .id(UUID.randomUUID().toString())
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expirationMs))
                .and()
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Long extractUserId(String token) {
        String subject = extractClaim(token, Claims::getSubject);
        return Long.valueOf(subject);

    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extarctAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extarctAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .requireIssuer("VMS")
                .requireAudience("VMS")
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, AppUserDetails userDetails) {
        final Long userId = extractUserId(token);
        return (userId.equals(userDetails.getId()) && !isTokenExpired(token));
    }

    public long getExpirationSeconds() {
        return expirationMs / 1000;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
