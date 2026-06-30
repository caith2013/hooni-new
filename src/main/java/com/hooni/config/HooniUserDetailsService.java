package com.hooni.config;

import com.hooni.db.User;
import com.hooni.db.UserSQLs;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Bridges Spring Security's authentication with the existing {@link UserSQLs}
 * data-access layer so that the formLogin flow works without rewriting UserSQLs.
 *
 * Original flow: LoginController fetched the User via UserSQLs.getUser(username),
 * compared passwords manually, and wrote a cookie.
 *
 * New flow: Spring Security calls this service during login, we return a
 * UserDetails object; Spring handles the password comparison and session cookie.
 */
@Service
public class HooniUserDetailsService implements UserDetailsService {

    private final UserSQLs userSQLs;

    public HooniUserDetailsService(UserSQLs userSQLs) {
        this.userSQLs = userSQLs;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userSQLs.getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Original app had no roles — every authenticated user gets ROLE_USER.
        // Add ROLE_ADMIN for admin-only controllers (e.g. AdmCurrentOrdersController).
        List<SimpleGrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_USER")
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUserName(),
                user.getPassword(),   // See SecurityConfig note about plain-text passwords
                authorities
        );
    }
}
