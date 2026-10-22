package com.furkan.gameapi.service;

import com.furkan.gameapi.model.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    private final String jwtSecret;

    public JwtService (@Value("${jwt.secret}")String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(AppUser appUser) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 1000 * 60 * 60);
        return Jwts.builder()
                .subject(appUser.getUsername())
                .claim("role",appUser.getRole().name())
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public Date extractExpiration (String token){
        return extractAllClaims(token).getExpiration();
    }

    public boolean isExpiration (String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token){
        try {
            extractAllClaims(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
