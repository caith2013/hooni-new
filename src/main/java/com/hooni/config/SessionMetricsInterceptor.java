package com.hooni.config;

import com.hooni.service.SessionMetricsService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.concurrent.ConcurrentHashMap;

/**
 * HTTP Interceptor to capture and track session metrics.
 * Records session creation, activity, and termination.
 */
@Slf4j
@Component
public class SessionMetricsInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionMetricsService sessionMetricsService;

    private static final String SESSION_RECORDED = "session_metrics_recorded";
    private static final ConcurrentHashMap<String, Boolean> activeSessions = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                String sessionId = session.getId();
                
                // Record session creation on first request
                if (session.getAttribute(SESSION_RECORDED) == null) {
                    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    String username = auth != null ? auth.getName() : "anonymous";
                    sessionMetricsService.recordSessionCreated(sessionId, username);
                    session.setAttribute(SESSION_RECORDED, true);
                    activeSessions.put(sessionId, true);
                }

                // Record session activity
                String activityType = request.getMethod() + " " + request.getRequestURI();
                sessionMetricsService.recordSessionActivity(
                        sessionId,
                        activityType,
                        "Path: " + request.getRequestURI() + ", Method: " + request.getMethod()
                );
            }
        } catch (Exception e) {
            log.error("Error in session metrics interceptor preHandle", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                // Session was invalidated - check if we need to record termination
                String sessionId = (String) request.getAttribute("jakarta.servlet.request.http.HttpSession.ID");
                if (sessionId != null && activeSessions.containsKey(sessionId)) {
                    sessionMetricsService.recordSessionTerminated(sessionId);
                    activeSessions.remove(sessionId);
                }
            }
        } catch (Exception e) {
            log.error("Error in session metrics interceptor afterCompletion", e);
        }
    }
}

