package de.propadmin.rentalmanager.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.models.UserAccount;

@Service
public class JwtService {

    private final JwtEncoder jwtEncoder;

    public JwtService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();

        Long userId = getUserId(authentication);

        JwtClaimsSet.Builder claimsBuilder = JwtClaimsSet.builder()
                .issuer("landlord-manager")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(3600)) // 1h
                .subject(authentication.getName())
                .claim("authorities",
                        authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()));
        if (userId != null) {
            claimsBuilder.claim("userId", userId);
        }
        JwtClaimsSet claims =claimsBuilder.build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
                         .getTokenValue();
    }

    private Long getUserId(Authentication authentication) {
        Long userId = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            if (userDetails instanceof UserAccount userAccount) {
                userId = userAccount.getId();
            }
        }
        return userId;
    }
}