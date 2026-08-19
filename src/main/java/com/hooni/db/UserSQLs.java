package com.hooni.db;

import com.hooni.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * UserSQLs — migrated to Spring.
 *
 * Original used Hibernate Session directly via MyDbSession.
 * Now delegates to the Spring Data JPA UserRepository.
 * Kept as a @Service so HooniUserDetailsService (and any other class that
 * injected UserSQLs) can continue to receive it via dependency injection.
 */
@Service
public class UserSQLs {

    private final UserRepository userRepository;

    public UserSQLs(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUser(String username) {
        return userRepository.findById(username).orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
