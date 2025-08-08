package de.propadmin.rentalmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class JwtService {
    
    @Autowired
    private JwtEncoder jwtEncoder;
    
    @Value("${jwt.expiration:86400}") // Default: 24 hours in seconds
    private long jwtExpirationSeconds;

    /**
     * Generate a JWT token for the authenticated user
     * @param authentication Spring Security Authentication object
     * @return JWT token as string
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("landlord-manager") // Your application name
                .issuedAt(now)
                .expiresAt(now.plus(jwtExpirationSeconds, ChronoUnit.SECONDS))
                .subject(authentication.getName())
                .claim("scope", "USER") // You can add more claims as needed
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}