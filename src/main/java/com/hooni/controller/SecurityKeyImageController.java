package com.hooni.controller;

import com.hooni.encrypt.HooniEncryptSystem;
import com.hooni.service.SecurityKeyService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * REST Controller for serving security key images.
 * Integrates with SecurityKeyService for Redis-based key management.
 */
@RestController
public class SecurityKeyImageController {
    private static final Logger logger = LoggerFactory.getLogger(SecurityKeyImageController.class);
    
    @Autowired
    private SecurityKeyService securityKeyService;
    
    private static final String SECURITY_KEY_ID = "securityKeyId";

    @GetMapping(value = "/security-key-image", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> getSecurityKeyImage(HttpSession session, HttpServletResponse response) throws IOException {
        logger.info("REST: Getting security key image for session: {}", session.getId());
        
        // Check if we already have a key ID for this session
        String keyId = (String) session.getAttribute(SECURITY_KEY_ID);
        String securityKey = null;
        
        if (keyId != null) {
            // Retrieve existing key from Redis
            securityKey = securityKeyService.getSecurityKey(keyId);
            logger.info("REST: Retrieved existing security key from Redis - KeyId: {}", keyId);
        }
        
        // If no key found in Redis or no keyId in session, create a new one
        if (securityKey == null || securityKey.isEmpty()) {
            logger.info("REST: Creating new security key for session: {}", session.getId());
            HooniEncryptSystem encryptSystem = new HooniEncryptSystem();
            encryptSystem.setupNewKey();
            securityKey = encryptSystem.getKey();
            
            // Store in Redis and get the ID
            keyId = securityKeyService.storeSecurityKey(session.getId(), securityKey);
            session.setAttribute(SECURITY_KEY_ID, keyId);
            this.addSessionCookie(response, keyId);
            logger.info("REST: New security key created and stored in Redis - KeyId: {} - TTL: 10 minutes", keyId);
        }
        
        // Generate key image from the security key
        BufferedImage keyImage = HooniEncryptSystem.createKeyImage(securityKey);
        
        if (keyImage == null) {
            logger.error("REST: Failed to create key image");
            return ResponseEntity.notFound().build();
        }
        
        // Convert BufferedImage to PNG bytes
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(keyImage, "png", baos);
        byte[] imageBytes = baos.toByteArray();
        
        logger.info("REST: Successfully created and returning key image, size: {} bytes", imageBytes.length);
        
        // Return with proper PNG content type and cache headers
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                .header(HttpHeaders.PRAGMA, "no-cache")
                .header(HttpHeaders.EXPIRES, "0")
                .body(imageBytes);
    }

    private void addSessionCookie(HttpServletResponse response, String sessionId) {
        Cookie sessionCookie = new Cookie("hooni_session", sessionId);
        sessionCookie.setMaxAge(5 * 60);  // 5 minutes
        sessionCookie.setPath("/");
        sessionCookie.setHttpOnly(true);  // Security: prevent JS access
        sessionCookie.setSecure(false);   // Set to true in production with HTTPS
        response.addCookie(sessionCookie);
    }
}

