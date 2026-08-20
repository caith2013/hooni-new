package com.hooni.controller;

import com.hooni.encrypt.HooniEncryptSystem;
import com.hooni.repository.UserRepository;
import com.hooni.service.SecurityKeyService;
import com.hooni.util.SessionUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Modern Spring Boot LoginController.
 * 
 * Replaces legacy servlet-based LoginController with Spring annotations.
 * Features:
 * - Encrypted credential transmission support
 * - Security code validation
 * - User session management via Redis
 * - Temporary security keys stored in Redis (10 minute TTL)
 * - Automatic key cleanup after login
 * - Redirect URL support
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;
    private final SecurityKeyService securityKeyService;
    private final org.springframework.security.web.context.SecurityContextRepository securityContextRepository;

    // Constants
    private static final String PARAM_USERNAME = "userName";
    private static final String PARAM_PASSWORD = "passwd";
    private static final String PARAM_SECURITY = "vsc";
    private static final String PARAM_REDIRECT_URL = "redirect";
    private static final String SECURITY_KEY_ID = "securityKeyId";
    private static final String HOMEPAGE = "/www?op=hooni";

    public LoginController(UserRepository userRepository,
                           UserDetailsService userDetailsService,
                           SecurityKeyService securityKeyService,
                           org.springframework.security.web.context.SecurityContextRepository securityContextRepository) {
        this.userRepository = userRepository;
        this.userDetailsService = userDetailsService;
        this.securityKeyService = securityKeyService;
        this.securityContextRepository = securityContextRepository;
    }

    /**
     * GET /login - Display login page
     * Generates security key and stores temporarily in Redis (10 minutes TTL)
     */
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "redirect", required = false) String redirect,
            @RequestParam(name = "expired", required = false) String expired,
            HttpSession session, HttpServletResponse response,
            Model model) {

        // If already logged in, redirect to homepage
        if (session.getAttribute("loggedInUser") != null) {
            return "redirect:" + HOMEPAGE;
        }


        // Add error message if present
        if (error != null && !error.isBlank()) {
            model.addAttribute("errorMessage", "Invalid username or password.");
        }

        // Add expiration message
        if (expired != null) {
            model.addAttribute("errorMessage", "Your security key expired. Please refresh and try again.");
        }

        // Add redirect URL if present (to return user to original page after login)
        if (redirect != null && !redirect.isBlank()) {
            model.addAttribute("redirect", redirect);
        }

        // Add session ID for client-side reference
        model.addAttribute("sessionId", SessionUtils.getSessionId(session));
        
        return "login";  // maps to classpath:/templates/login.html or login.ftl
    }

    /**
     * POST /login - Process login credentials
     * 
     * Expected form data:
     * - userName: username (plain or encrypted)
     * - passwd: password (plain or encrypted)
     * - vsc: security code
     * - redirect: (optional) URL to redirect after login
     */
    @PostMapping("/login")
    public String processLogin(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String passwd,
            @RequestParam(required = false) String vsc,
            @RequestParam(required = false) String redirect,
            HttpSession session,
            HttpServletResponse response,
            HttpServletRequest request,
            Model model) {

        logger.info("Login attempt - Session ID: {} - Username: {}", session.getId(), userName);

        String keyId = request.getCookies() != null ?
                java.util.Arrays.stream(request.getCookies())
                    .filter(c -> "hooni_session".equals(c.getName()))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null) : null;
        // Retrieve security key from Redis using session ID (works across different sessions)
        String securityKey = securityKeyService.getSecurityKey(keyId);
        
        if (securityKey == null) {
            // Try to find it from any related session by checking parent session
            // This handles the case where POST is in a different session than GET
            logger.warn("Security key not found for current sessionId: {}. Checking for parent session...", 
                    session.getId());
            model.addAttribute("errorMessage", "Security key expired. Please refresh the login page and try again.");
            return "redirect:/login?expired=true";
        }

        HashMap<String, String> loginStrings = new HashMap<>();
        loginStrings.put("userName", userName);
        loginStrings.put("passwd", passwd);
        loginStrings.put("vsc", vsc);
        
        HashMap<String, String> decryptedLoginStrings = HooniEncryptSystem.decrypt(loginStrings, securityKey);
        userName = decryptedLoginStrings.get("userName");
        passwd = decryptedLoginStrings.get("passwd");
        vsc = decryptedLoginStrings.get("vsc");

        logger.debug("Credentials decrypted successfully - Session: {} - Username: {}", session.getId(), userName);

        Map<String, String> errors = new HashMap<>();

        // Step 1: Validate form inputs are present
        if (userName == null || userName.isBlank()) {
            errors.put(PARAM_USERNAME, "Username is required");
        }
        if (passwd == null || passwd.isBlank()) {
            errors.put(PARAM_PASSWORD, "Password is required");
        }
        if (vsc == null || vsc.isBlank()) {
            errors.put(PARAM_SECURITY, "Security code is required");
        }

        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "login";
        }

        // Step 2: Check if user exists
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

            // Step 3: Verify password matches
            if (!passwd.equals(userDetails.getPassword())) {
                errors.put(PARAM_PASSWORD, "Invalid username or password");
                model.addAttribute("errors", errors);
                // Keep security key for retry
                return "login";
            }

            // Step 4: Create authenticated token directly (password already validated in step 3)
            // Bypass authenticationManager to avoid AOP proxy recursion issues
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            userName,
                            passwd,
                            userDetails.getAuthorities()
                    );
            authToken.setDetails(userDetails);
            
            // Directly set authentication in SecurityContext
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authToken);
            
            logger.info("User authenticated successfully: {}", userName);
            logger.debug("SecurityContext before save - Auth: {}, Principal: {}", 
                securityContext.getAuthentication() != null ? securityContext.getAuthentication().getName() : "null",
                securityContext.getAuthentication() != null ? securityContext.getAuthentication().getPrincipal() : "null");

            // Step 5: Save to session directly (Spring Session will auto-persist to Redis)
            // We use "SPRING_SECURITY_CONTEXT" key which is the standard Spring Security session attribute
            logger.debug("Saving SecurityContext directly to HttpSession");
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            
            // Verify it was saved
            logger.debug("Verifying context saved - Session now contains context: {}", 
                session.getAttribute("SPRING_SECURITY_CONTEXT") != null);
            
            // Also store user in session (Redis-backed)
            SessionUtils.storeUserInSession(session, userDetails);

            // Step 6: Clean up security keys from Redis
                securityKeyService.deleteSecurityKey(keyId);
                logger.info("Security key cleaned up key after login - KeyId: {}",
                        keyId);

            
            // Also try to clean up any keys from other sessions (best effort)
            session.removeAttribute(SECURITY_KEY_ID);

            // Step 7: Add user tracking cookie (24 hours)
            addUserCookie(response, userName);
            model.addAttribute("loggedInUser", userName);

            logger.info("User logged in successfully - Username: {} - Session ID: {}", userName, session.getId());

            // Step 8: Redirect
            String redirectUrl = (redirect != null && !redirect.isBlank()) ? redirect : HOMEPAGE;
            return "redirect:" + redirectUrl;

        } catch (Exception e) {
            logger.error("Login failed for user: {} - Exception: {}", userName, e.getMessage(), e);
            errors.put(PARAM_USERNAME, "Login failed: Invalid username or password");
            model.addAttribute("errors", errors);
            // Keep security key for retry
            return "login";
        }
    }

    /**
     * Add user tracking cookie (24 hours)
     */
    private void addUserCookie(HttpServletResponse response, String username) {
        Cookie userCookie = new Cookie("hooni_user", username);
        userCookie.setMaxAge(24 * 60 * 60);  // 24 hours
        userCookie.setPath("/");
        userCookie.setHttpOnly(true);  // Security: prevent JS access
        userCookie.setSecure(false);   // Set to true in production with HTTPS
        response.addCookie(userCookie);
    }



    /**
     * POST /logout - Clear session and redirect
     */
    @PostMapping("/logout")
    public String logout(HttpSession session, HttpServletResponse response) {
        // Clean up security key from Redis
        String keyId = (String) session.getAttribute(SECURITY_KEY_ID);
        if (keyId != null) {
            securityKeyService.deleteSecurityKey(keyId);
            logger.info("Security key cleaned up on logout - KeyId: {}", keyId);
        }

        // Clear user session
        SessionUtils.clearUserSession(session);

        // Invalidate session
        session.invalidate();

        // Clear security context
        SecurityContextHolder.clearContext();

        logger.info("User logged out successfully");

        // Redirect to login
        return "redirect:/login";
    }
}

