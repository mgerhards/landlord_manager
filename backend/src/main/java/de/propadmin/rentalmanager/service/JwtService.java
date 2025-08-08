package de.propadmin.rentalmanager.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import java.util.Date;

@Service
public class JwtService {
    private final String jwtSecret = "your-very-strong-secret-key"; // Use env/config in production
    private final long jwtExpirationMs = 86400000; // 1 day

    public String generateToken(Authentication authentication) {
        return Jwts.builder()
                .setSubject(authentication.getName())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
}