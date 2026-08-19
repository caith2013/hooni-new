package com.hooni.util;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Extended utility class for advanced Redis session tracking and monitoring.
 * Complements SessionUtils with tracking, tagging, and analysis capabilities.
 */
@Slf4j
public class AdvancedSessionUtils {

    // Session attribute keys
    public static final String LOGIN_TIME = "login_time";
    public static final String LAST_ACTIVITY_TIME = "last_activity_time";
    public static final String ACTIVITY_COUNT = "activity_count";
    public static final String SESSION_TAGS = "session_tags";
    public static final String DEVICE_INFO = "device_info";
    public static final String IP_ADDRESS = "ip_address";
    public static final String USER_AGENT = "user_agent";

    /**
     * Track login event with timestamp and session initialization.
     */
    public static void trackLoginEvent(HttpSession session, UserDetails principal, String ipAddress, String userAgent) {
        SessionUtils.storeUserInSession(session, principal);
        
        long loginTime = System.currentTimeMillis();
        session.setAttribute(LOGIN_TIME, loginTime);
        session.setAttribute(LAST_ACTIVITY_TIME, loginTime);
        session.setAttribute(ACTIVITY_COUNT, 0);
        session.setAttribute(IP_ADDRESS, ipAddress);
        session.setAttribute(USER_AGENT, userAgent);

        log.info("Login tracked for user: {} from IP: {} with session: {}", 
                principal.getUsername(), ipAddress, session.getId());
    }

    /**
     * Track user activity and update activity counter.
     */
    public static void trackActivity(HttpSession session, String activityName) {
        Integer activityCount = (Integer) session.getAttribute(ACTIVITY_COUNT);
        if (activityCount == null) {
            activityCount = 0;
        }
        
        session.setAttribute(ACTIVITY_COUNT, activityCount + 1);
        session.setAttribute(LAST_ACTIVITY_TIME, System.currentTimeMillis());

        log.debug("Activity tracked: {} - Count: {} - Session: {}", 
                activityName, activityCount + 1, session.getId());
    }

    /**
     * Get session duration in seconds.
     */
    public static Long getSessionDurationSeconds(HttpSession session) {
        Object loginTimeObj = session.getAttribute(LOGIN_TIME);
        if (loginTimeObj == null) {
            return 0L;
        }

        Long loginTime = (Long) loginTimeObj;
        return (System.currentTimeMillis() - loginTime) / 1000;
    }

    /**
     * Get last activity time.
     */
    public static Long getLastActivityTime(HttpSession session) {
        Object lastActivityObj = session.getAttribute(LAST_ACTIVITY_TIME);
        return lastActivityObj != null ? (Long) lastActivityObj : 0L;
    }

    /**
     * Get activity count.
     */
    public static Integer getActivityCount(HttpSession session) {
        Object countObj = session.getAttribute(ACTIVITY_COUNT);
        return countObj != null ? (Integer) countObj : 0;
    }

    /**
     * Check if session is idle (no activity for specified seconds).
     */
    public static boolean isSessionIdle(HttpSession session, int idleThresholdSeconds) {
        Long lastActivityTime = getLastActivityTime(session);
        long elapsedSeconds = (System.currentTimeMillis() - lastActivityTime) / 1000;
        return elapsedSeconds > idleThresholdSeconds;
    }

    /**
     * Add tag to session for categorization/grouping.
     */
    public static void addSessionTag(HttpSession session, String tag) {
        @SuppressWarnings("unchecked")
        java.util.Set<String> tags = (java.util.Set<String>) session.getAttribute(SESSION_TAGS);
        if (tags == null) {
            tags = new java.util.HashSet<>();
        }
        tags.add(tag);
        session.setAttribute(SESSION_TAGS, tags);

        log.debug("Tag added to session: {} - Tag: {}", session.getId(), tag);
    }

    /**
     * Get all session tags.
     */
    @SuppressWarnings("unchecked")
    public static java.util.Set<String> getSessionTags(HttpSession session) {
        java.util.Set<String> tags = (java.util.Set<String>) session.getAttribute(SESSION_TAGS);
        return tags != null ? tags : new java.util.HashSet<>();
    }

    /**
     * Get session info summary.
     */
    public static String getSessionInfoSummary(HttpSession session) {
        String username = SessionUtils.getUserFromSession(session);
        Long duration = getSessionDurationSeconds(session);
        Integer activityCount = getActivityCount(session);
        String ipAddress = (String) session.getAttribute(IP_ADDRESS);

        return String.format(
                "Session[id=%s, user=%s, duration=%ds, activities=%d, ip=%s]",
                session.getId(), username, duration, activityCount, ipAddress
        );
    }
}
