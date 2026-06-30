package com.hooni.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * JPA configuration.
 *
 * Spring Boot auto-configures the EntityManagerFactory and
 * TransactionManager from application.properties (datasource + jpa.*).
 * This class just enables repository scanning and declarative transactions.
 *
 * The original code used Hibernate's SessionFactory directly via
 * HibernateSessionFactory / MyDbSession.  The new approach is:
 *   - Entities annotated with @Entity / @Table (see db package)
 *   - Spring Data JPA repositories for simple CRUD
 *   - @Transactional on service methods replacing manual beginTransaction/commit/rollback
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.hooni.repository")
public class JpaConfig {
    // All DataSource, EntityManagerFactory, and PlatformTransactionManager beans
    // are auto-configured by Spring Boot via spring-boot-starter-data-jpa +
    // the properties in application.properties.
}
