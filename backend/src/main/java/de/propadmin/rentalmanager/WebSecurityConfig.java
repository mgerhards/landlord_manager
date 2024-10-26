package de.propadmin.rentalmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (not recommended for production)
        .authorizeHttpRequests(authz -> authz
            .requestMatchers("/", "/home", "/login", "/error").permitAll() // Ensure login and home pages are accessible
            .anyRequest().authenticated() // All other pages require authentication
        )
        .formLogin(form -> form
            .loginPage("/login") // Custom login page
            .defaultSuccessUrl("/home", true) // Redirect to home page after successful login
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout") // URL for logout
            .logoutSuccessUrl("/login?logout") // Redirect to login page after logout
            .permitAll()
        );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("password")) // Use a more secure password in production
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}