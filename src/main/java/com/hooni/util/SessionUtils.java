package com.hooni.util;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Utility class for managing HTTP session operations across controllers.
 */
public class SessionUtils {

    public static void storeUserInSession(HttpSession session, UserDetails principal) {
        if (principal != null) {
            session.setAttribute("loggedInUser", principal.getUsername());
            session.setAttribute("userDetails", principal);
        }
    }

    public static String getUserFromSession(HttpSession session) {
        Object user = session.getAttribute("loggedInUser");
        return user != null ? user.toString() : null;
    }

    public static UserDetails getUserDetailsFromSession(HttpSession session) {
        return (UserDetails) session.getAttribute("userDetails");
    }

    public static void setSessionAttribute(HttpSession session, String key, Object value) {
        session.setAttribute(key, value);
    }

    public static Object getSessionAttribute(HttpSession session, String key) {
        return session.getAttribute(key);
    }

    public static void removeSessionAttribute(HttpSession session, String key) {
        session.removeAttribute(key);
    }

    public static void clearUserSession(HttpSession session) {
        session.removeAttribute("loggedInUser");
        session.removeAttribute("userDetails");
    }

    public static String getSessionId(HttpSession session) {
        return session.getId();
    }
}
