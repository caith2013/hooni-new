package com.hooni.service;

import com.hooni.db.User;
import com.hooni.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Custom UserDetailsService implementation for Spring Security.
 * Loads user credentials from database and provides UserDetails for authentication.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Load user by username for Spring Security authentication.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUserName())
            .password(user.getPassword())
            .authorities(Collections.emptyList())
            .disabled(!"ACTIVE".equals(user.getStatus()))  // User disabled if not ACTIVE
            .build();
    }

    /**
     * Load user by email (optional helper method)
     */
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + email));

        return org.springframework.security.core.userdetails.User.builder()
            .username(user.getUserName())
            .password(user.getPassword())
            .authorities(Collections.emptyList())
            .disabled(!"ACTIVE".equals(user.getStatus()))
            .build();
    }
}
