package com.hooni.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/**
 * Spring Session Configuration for Redis-backed distributed sessions.
 * 
 * This configuration enables sessions to be stored in Redis, allowing
 * session sharing across multiple server instances in a distributed system.
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 1800) // 30 minutes
public class SessionConfig {

    /**
     * Configure session ID resolution using cookies (default for web browsers).
     * The cookie name is configured in application.properties as server.servlet.session.cookie.name=HOONI_SESSION
     */
    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        return new CookieHttpSessionIdResolver();
    }
}
