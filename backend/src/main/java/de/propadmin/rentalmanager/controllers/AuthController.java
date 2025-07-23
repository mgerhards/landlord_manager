package de.propadmin.rentalmanager.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.propadmin.rentalmanager.service.JwtService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth controller is working!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        try {
            // Print debug info
            System.out.println("Login attempt with username: " + username);
            
            // Attempt to authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            
            // If authentication was successful, set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            // Generate JWT token
            String token = jwtService.generateToken(authentication);
            
            // Return the JWT token as plain text (as expected by the frontend)
            return ResponseEntity.ok(token);
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials for user: " + username);
            return ResponseEntity.status(401).body("Invalid username or password");
        } catch (Exception e) {
            System.out.println("Authentication error: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body("Authentication error: " + e.getMessage());
        }
    }
    
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().body("Logged out successfully");
    }
}