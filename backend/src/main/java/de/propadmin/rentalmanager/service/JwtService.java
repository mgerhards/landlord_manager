package de.propadmin.rentalmanager.service;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import de.propadmin.rentalmanager.dto.AppUserDetailsDTO;
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
        String role = getUserRole(authentication);
        String email = getUserEmail(authentication);

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
        if (role != null) {
            claimsBuilder.claim("role", role);
        }
        if (email != null) {
            claimsBuilder.claim("email", email);
        }
        JwtClaimsSet claims = claimsBuilder.build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(header, claims))
                         .getTokenValue();
    }

    private Long getUserId(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AppUserDetailsDTO appUser) {
            return appUser.getId();
        }
        return null;
    }

    private String getUserRole(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AppUserDetailsDTO appUser) {
            UserAccount account = appUser.getUserAccount();
            if (account.getRole() != null) {
                return account.getRole().name();
            }
        }
        return null;
    }

    private String getUserEmail(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        if (principal instanceof AppUserDetailsDTO appUser) {
            return appUser.getUserAccount().getEmail();
        }
        return null;
    }
}