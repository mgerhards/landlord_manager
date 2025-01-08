package de.propadmin.rentalmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;

import de.propadmin.rentalmanager.models.UserAccount;
import de.propadmin.rentalmanager.repositories.UserRepository;
import de.propadmin.rentalmanager.utils.exeptions.UsernameNotFoundException;

@Configuration
public class AuthorizationServerConfig {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthorizationServerConfig(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // Initialize the OAuth2 Authorization Server configurer
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();

        // Apply the Authorization Server configuration
        http.apply(authorizationServerConfigurer);
        
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults()); // Enables OpenID Connect 1.0 support
            
            http
            .securityMatcher("/oauth2/**") // Apply this filter chain only to OAuth2-related endpoints            
            //.cors(cors -> cors.disable()) // Disable CORS for OAuth2 endpoints
            .cors(Customizer.withDefaults()) // Enable CORS for OAuth2 endpoints
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/oauth2/token").permitAll() // Allow public access to the token endpoint
                .requestMatchers("/oauth2/jwks").permitAll()  // Allow public access to JWKS
                .requestMatchers("/oauth2/authorize").authenticated() // Secure authorization endpoint
                .anyRequest().authenticated()                         // Secure any other endpoints
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/oauth2/token", "/login")) // Disable CSRF for token endpoint
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            )
            .formLogin(Customizer.withDefaults()) // Default form login for OAuth2 flow
            .oauth2ResourceServer(oauth2 -> oauth2.jwt()); // Optional: Enable JWT support if needed
    
            
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> {
            UserAccount user = userRepository.findByEmail(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return User.withUsername(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();
        };
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("client")
            .clientSecret(passwordEncoder.encode("secret"))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri("http://localhost:5173/")
            .scope("openid")
            .scope("profile")
            .build();

        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
            .tokenEndpoint("/oauth2/token")
            .build();
    }


}