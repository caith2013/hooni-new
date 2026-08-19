package com.hooni.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.UUID;

/**
 * Service for managing temporary security keys in Redis.
 * Security keys are stored temporarily for login form encryption/decryption.
 * Keys are automatically removed after successful login or after TTL expiration.
 */
@Slf4j
@Service
public class SecurityKeyService {

    private static final String SECURITY_KEY_PREFIX = "hooni:security:key:";
    private static final long KEY_TTL_SECONDS = 600; // 10 minutes
    private static final long SESSION_KEY_TTL_SECONDS = 3600; // 1 hour

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * Generate and store a new security key in Redis.
     * Returns the key ID to be used in session/cookies.
     * 
     * @param sessionId The session ID
     * @param securityKey The actual security key value
     * @return Key ID for tracking
     */
    public String storeSecurityKey(String sessionId, String securityKey) {
        try {
            String keyId = UUID.randomUUID().toString();
            String redisKey = SECURITY_KEY_PREFIX + keyId;
            
            // Store in Redis with TTL
            redisTemplate.opsForValue().set(redisKey, securityKey, Duration.ofSeconds(KEY_TTL_SECONDS));
            
            // Also store mapping of sessionId to keyId for quick lookup
            String sessionKeyMapping = "hooni:security:session:" + sessionId;
            redisTemplate.opsForValue().set(sessionKeyMapping, keyId, Duration.ofSeconds(SESSION_KEY_TTL_SECONDS));
            
            log.info("Security key stored in Redis - KeyId: {} - SessionId: {} - TTL: {}s", 
                    keyId, sessionId, KEY_TTL_SECONDS);
            
            return keyId;
        } catch (Exception e) {
            log.error("Error storing security key for session: {}", sessionId, e);
            throw new RuntimeException("Failed to store security key", e);
        }
    }

    /**
     * Retrieve security key from Redis using key ID.
     * 
     * @param keyId The key ID returned from storeSecurityKey
     * @return The security key value, or null if not found or expired
     */
    public String getSecurityKey(String keyId) {
        try {
            if (keyId == null || keyId.isBlank()) {
                log.warn("Attempted to retrieve security key with null/empty keyId");
                return null;
            }
            
            String redisKey = SECURITY_KEY_PREFIX + keyId;
            Object value = redisTemplate.opsForValue().get(redisKey);
            
            if (value != null) {
                log.debug("Security key retrieved from Redis - KeyId: {}", keyId);
                return value.toString();
            } else {
                log.warn("Security key not found or expired - KeyId: {}", keyId);
                return null;
            }
        } catch (Exception e) {
            log.error("Error retrieving security key for keyId: {}", keyId, e);
            return null;
        }
    }

    /**
     * Retrieve security key from Redis using session ID.
     * This method works across different sessions by looking up the mapping in Redis.
     * 
     * @param sessionId The session ID
     * @return The security key value, or null if not found or expired
     */
    public String getSecurityKeyBySessionId(String sessionId) {
        try {
            String sessionKeyMapping = "hooni:security:session:" + sessionId;
            Object keyIdObj = redisTemplate.opsForValue().get(sessionKeyMapping);
            
            if (keyIdObj != null) {
                String keyId = keyIdObj.toString();
                log.debug("Found keyId in Redis for sessionId: {} - KeyId: {}", sessionId, keyId);
                return getSecurityKey(keyId);
            } else {
                log.warn("No security key mapping found for sessionId: {}", sessionId);
                return null;
            }
        } catch (Exception e) {
            log.error("Error retrieving security key for sessionId: {}", sessionId, e);
            return null;
        }
    }

    /**
     * Retrieve just the key ID from Redis using session ID.
     * Useful when you need the keyId itself, not the actual key.
     * 
     * @param sessionId The session ID
     * @return The key ID, or null if not found
     */
    public String getSecurityKeyIdBySessionId(String sessionId) {
        try {
            String sessionKeyMapping = "hooni:security:session:" + sessionId;
            Object keyIdObj = redisTemplate.opsForValue().get(sessionKeyMapping);
            
            if (keyIdObj != null) {
                String keyId = keyIdObj.toString();
                log.debug("Retrieved keyId from Redis for sessionId: {} - KeyId: {}", sessionId, keyId);
                return keyId;
            } else {
                log.warn("No security key ID mapping found for sessionId: {}", sessionId);
                return null;
            }
        } catch (Exception e) {
            log.error("Error retrieving security key ID for sessionId: {}", sessionId, e);
            return null;
        }
    }

    /**
     * Delete security key from Redis (typically after successful login).
     * 
     * @param keyId The key ID to delete
     */
    public void deleteSecurityKey(String keyId) {
        try {
            if (keyId == null || keyId.isBlank()) {
                return;
            }
            
            String redisKey = SECURITY_KEY_PREFIX + keyId;
            Boolean deleted = redisTemplate.delete(redisKey);
            
            log.info("Security key deleted from Redis - KeyId: {} - Deleted: {}", keyId, deleted);
        } catch (Exception e) {
            log.error("Error deleting security key for keyId: {}", keyId, e);
        }
    }

    /**
     * Delete security key using session ID.
     * 
     * @param sessionId The session ID
     */
    public void deleteSecurityKeyBySessionId(String sessionId) {
        try {
            String sessionKeyMapping = "hooni:security:session:" + sessionId;
            Object keyIdObj = redisTemplate.opsForValue().get(sessionKeyMapping);
            
            if (keyIdObj != null) {
                String keyId = keyIdObj.toString();
                deleteSecurityKey(keyId);
                redisTemplate.delete(sessionKeyMapping);
                log.info("Security key and mapping deleted for sessionId: {}", sessionId);
            }
        } catch (Exception e) {
            log.error("Error deleting security key for sessionId: {}", sessionId, e);
        }
    }

    /**
     * Check if security key exists and is valid.
     * 
     * @param keyId The key ID to check
     * @return true if key exists and is valid
     */
    public boolean isSecurityKeyValid(String keyId) {
        try {
            if (keyId == null || keyId.isBlank()) {
                return false;
            }
            
            String redisKey = SECURITY_KEY_PREFIX + keyId;
            return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
        } catch (Exception e) {
            log.error("Error checking security key validity for keyId: {}", keyId, e);
            return false;
        }
    }

    /**
     * Get remaining TTL for a security key (in seconds).
     * 
     * @param keyId The key ID
     * @return Remaining TTL in seconds, or -1 if not found
     */
    public Long getSecurityKeyTtl(String keyId) {
        try {
            String redisKey = SECURITY_KEY_PREFIX + keyId;
            Long ttl = redisTemplate.getExpire(redisKey);
            
            if (ttl != null && ttl > 0) {
                log.debug("Security key TTL - KeyId: {} - TTL: {}s", keyId, ttl);
            }
            
            return ttl;
        } catch (Exception e) {
            log.error("Error retrieving TTL for security key: {}", keyId, e);
            return -1L;
        }
    }
}
