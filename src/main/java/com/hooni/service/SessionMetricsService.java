package com.hooni.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Service for tracking and monitoring Redis session metrics.
 * Provides insights into session activity, duration, and usage patterns.
 */
@Slf4j
@Service
public class SessionMetricsService {

    private static final String SESSION_METRICS_KEY = "hooni:metrics:sessions";
    private static final String ACTIVE_USERS_KEY = "hooni:metrics:active_users";
    private static final String SESSION_DURATION_KEY = "hooni:metrics:session_duration";
    private static final String SESSION_COUNT_KEY = "hooni:metrics:total_sessions";

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Record a new session creation.
     */
    public void recordSessionCreated(String sessionId, String username) {
        try {
            Map<String, Object> sessionMetrics = new HashMap<>();
            sessionMetrics.put("sessionId", sessionId);
            sessionMetrics.put("username", username);
            sessionMetrics.put("createdAt", System.currentTimeMillis());
            sessionMetrics.put("lastActivity", System.currentTimeMillis());
            sessionMetrics.put("status", "active");

            String key = SESSION_METRICS_KEY + ":" + sessionId;
            redisTemplate.opsForHash().putAll(key, sessionMetrics);
            redisTemplate.expire(key, java.time.Duration.ofDays(1));

            incrementActiveUsersCount();
            incrementSessionCount();

            log.info("Session created: {} for user: {}", sessionId, username);
        } catch (Exception e) {
            log.error("Error recording session creation for sessionId: {}", sessionId, e);
        }
    }

    /**
     * Record session activity.
     */
    public void recordSessionActivity(String sessionId, String activityType, String details) {
        try {
            String key = SESSION_METRICS_KEY + ":" + sessionId;
            redisTemplate.opsForHash().put(key, "lastActivity", System.currentTimeMillis());
            redisTemplate.opsForHash().put(key, "lastActivityType", activityType);
            redisTemplate.opsForHash().put(key, "lastActivityDetails", details);

            log.debug("Session activity recorded: {} - Type: {}", sessionId, activityType);
        } catch (Exception e) {
            log.error("Error recording session activity for sessionId: {}", sessionId, e);
        }
    }

    /**
     * Record session termination.
     */
    public void recordSessionTerminated(String sessionId) {
        try {
            String key = SESSION_METRICS_KEY + ":" + sessionId;
            Long createdAt = (Long) redisTemplate.opsForHash().get(key, "createdAt");
            Long duration = System.currentTimeMillis() - (createdAt != null ? createdAt : 0);

            redisTemplate.opsForHash().put(key, "status", "terminated");
            redisTemplate.opsForHash().put(key, "terminatedAt", System.currentTimeMillis());
            redisTemplate.opsForHash().put(key, "duration", duration);

            redisTemplate.opsForList().rightPush(SESSION_DURATION_KEY, duration);
            decrementActiveUsersCount();

            log.info("Session terminated: {} - Duration: {}ms", sessionId, duration);
        } catch (Exception e) {
            log.error("Error recording session termination for sessionId: {}", sessionId, e);
        }
    }

    /**
     * Get active session count.
     */
    public Long getActiveSessionCount() {
        try {
            Object count = redisTemplate.opsForValue().get(ACTIVE_USERS_KEY);
            return count != null ? Long.parseLong(count.toString()) : 0L;
        } catch (Exception e) {
            log.error("Error retrieving active session count", e);
            return 0L;
        }
    }

    /**
     * Get total sessions created.
     */
    public Long getTotalSessionsCreated() {
        try {
            Object count = redisTemplate.opsForValue().get(SESSION_COUNT_KEY);
            return count != null ? Long.parseLong(count.toString()) : 0L;
        } catch (Exception e) {
            log.error("Error retrieving total sessions count", e);
            return 0L;
        }
    }

    /**
     * Get average session duration in seconds.
     */
    public Double getAverageSessionDuration() {
        try {
            List<Object> durations = redisTemplate.opsForList().range(SESSION_DURATION_KEY, 0, -1);
            if (durations == null || durations.isEmpty()) {
                return 0.0;
            }
            Double sum = durations.stream()
                    .mapToDouble(d -> Double.parseDouble(d.toString()))
                    .sum();
            return sum / durations.size() / 1000; // Convert to seconds
        } catch (Exception e) {
            log.error("Error calculating average session duration", e);
            return 0.0;
        }
    }

    /**
     * Get session metrics by ID.
     */
    public Map<Object, Object> getSessionMetrics(String sessionId) {
        try {
            String key = SESSION_METRICS_KEY + ":" + sessionId;
            Map<Object, Object> metrics = new HashMap<>();
            Object username = redisTemplate.opsForHash().get(key, "username");
            Object createdAt = redisTemplate.opsForHash().get(key, "createdAt");
            Object lastActivity = redisTemplate.opsForHash().get(key, "lastActivity");
            Object status = redisTemplate.opsForHash().get(key, "status");
            Object lastActivityType = redisTemplate.opsForHash().get(key, "lastActivityType");
            Object lastActivityDetails = redisTemplate.opsForHash().get(key, "lastActivityDetails");
            Object duration = redisTemplate.opsForHash().get(key, "duration");
            Object terminatedAt = redisTemplate.opsForHash().get(key, "terminatedAt");

            if (username != null) metrics.put("username", username);
            if (createdAt != null) metrics.put("createdAt", createdAt);
            if (lastActivity != null) metrics.put("lastActivity", lastActivity);
            if (status != null) metrics.put("status", status);
            if (lastActivityType != null) metrics.put("lastActivityType", lastActivityType);
            if (lastActivityDetails != null) metrics.put("lastActivityDetails", lastActivityDetails);
            if (duration != null) metrics.put("duration", duration);
            if (terminatedAt != null) metrics.put("terminatedAt", terminatedAt);

            return metrics;
        } catch (Exception e) {
            log.error("Error retrieving session metrics for sessionId: {}", sessionId, e);
            return Collections.emptyMap();
        }
    }

    /**
     * Get all active sessions.
     */
    public List<Map<Object, Object>> getAllActiveSessions() {
        try {
            Set<String> keys = redisTemplate.keys(SESSION_METRICS_KEY + ":*");
            List<Map<Object, Object>> activeSessions = new ArrayList<>();

            if (keys != null) {
                for (String key : keys) {
                    Object status = redisTemplate.opsForHash().get(key, "status");
                    if ("active".equals(status)) {
                        Map<Object, Object> metrics = new HashMap<>();
                        Object username = redisTemplate.opsForHash().get(key, "username");
                        Object createdAt = redisTemplate.opsForHash().get(key, "createdAt");
                        Object lastActivity = redisTemplate.opsForHash().get(key, "lastActivity");
                        Object lastActivityType = redisTemplate.opsForHash().get(key, "lastActivityType");

                        if (username != null) metrics.put("username", username);
                        if (createdAt != null) metrics.put("createdAt", createdAt);
                        if (lastActivity != null) metrics.put("lastActivity", lastActivity);
                        if (lastActivityType != null) metrics.put("lastActivityType", lastActivityType);
                        metrics.put("status", "active");

                        activeSessions.add(metrics);
                    }
                }
            }
            return activeSessions;
        } catch (Exception e) {
            log.error("Error retrieving all active sessions", e);
            return Collections.emptyList();
        }
    }

    /**
     * Get overall session statistics.
     */
    public SessionStatistics getSessionStatistics() {
        try {
            Long activeCount = getActiveSessionCount();
            Long totalCount = getTotalSessionsCreated();
            Double avgDuration = getAverageSessionDuration();

            return SessionStatistics.builder()
                    .activeSessionsCount(activeCount)
                    .totalSessionsCreated(totalCount)
                    .averageSessionDurationSeconds(avgDuration)
                    .timestamp(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.error("Error calculating session statistics", e);
            return SessionStatistics.builder()
                    .activeSessionsCount(0L)
                    .totalSessionsCreated(0L)
                    .averageSessionDurationSeconds(0.0)
                    .timestamp(LocalDateTime.now())
                    .build();
        }
    }

    private void incrementActiveUsersCount() {
        redisTemplate.opsForValue().increment(ACTIVE_USERS_KEY);
    }

    private void decrementActiveUsersCount() {
        redisTemplate.opsForValue().decrement(ACTIVE_USERS_KEY);
    }

    private void incrementSessionCount() {
        redisTemplate.opsForValue().increment(SESSION_COUNT_KEY);
    }
}
