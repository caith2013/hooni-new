package com.hooni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

/**
 * Spring Security Configuration for the Hooni application.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure BCrypt password encoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    /**
     * Create AuthenticationManager bean for programmatic authentication.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig)
            throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configure security filter chain for HTTP requests.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Session management - persist authentication to session so it survives across requests
            .securityContext(context -> context
                .securityContextRepository(securityContextRepository())
            )
            
            // Authorization rules
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/logout", "/register", "/", "/error", "/error/**", "/security-key-image").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/static/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                //.requestMatchers(org.springframework.http.HttpMethod.POST, "/**").authenticated()
                .anyRequest().permitAll()
            )
            
            // Login configuration - disabled, using custom LoginController instead
            .formLogin(login -> login.disable())
            
            // Logout configuration
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            // CSRF protection - disable for simplicity (enable in production with proper token handling)
            .csrf(csrf -> csrf.disable())
            
            // Exception handling
            .exceptionHandling(exception -> exception
                .accessDeniedPage("/error/403")
            );

        return http.build();
    }

    /**
     * Create SecurityContextRepository bean to persist authentication in session.
     * This ensures authentication survives across requests (especially after redirects).
     */
    @Bean
    public SecurityContextRepository securityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }
}