package com.hooni.repository;

import com.hooni.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data JPA Repository for User entity.
 * Replaces legacy UserSQLs manual query builder.
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);

    /**
     * Find user by username
     */
    Optional<User> findByUserName(String userName);

    /**
     * Check if username exists
     */
    boolean existsByUserName(String userName);

    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);

}
