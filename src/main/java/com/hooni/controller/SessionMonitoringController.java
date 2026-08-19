package com.hooni.controller;

import com.hooni.service.SessionMetricsService;
import com.hooni.service.SessionStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST API for monitoring Redis session metrics.
 * Restricted to admin users only.
 */
@Slf4j
@RestController
@RequestMapping("/api/sessions")
public class SessionMonitoringController {

    @Autowired
    private SessionMetricsService sessionMetricsService;

    /**
     * Get overall session statistics.
     * GET /api/sessions/statistics
     */
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SessionStatistics> getSessionStatistics() {
        log.info("Fetching session statistics");
        SessionStatistics stats = sessionMetricsService.getSessionStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get count of active sessions.
     * GET /api/sessions/active/count
     */
    @GetMapping("/active/count")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> getActiveSessionCount() {
        log.info("Fetching active session count");
        Long count = sessionMetricsService.getActiveSessionCount();
        return ResponseEntity.ok(Map.of("activeSessionsCount", count));
    }

    /**
     * Get all active sessions.
     * GET /api/sessions/active
     */
    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Map<Object, Object>>> getAllActiveSessions() {
        log.info("Fetching all active sessions");
        List<Map<Object, Object>> sessions = sessionMetricsService.getAllActiveSessions();
        return ResponseEntity.ok(sessions);
    }

    /**
     * Get specific session metrics.
     * GET /api/sessions/{sessionId}/metrics
     */
    @GetMapping("/{sessionId}/metrics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<Object, Object>> getSessionMetrics(@PathVariable String sessionId) {
        log.info("Fetching metrics for session: {}", sessionId);
        Map<Object, Object> metrics = sessionMetricsService.getSessionMetrics(sessionId);
        if (metrics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(metrics);
    }

    /**
     * Get average session duration.
     * GET /api/sessions/average-duration
     */
    @GetMapping("/average-duration")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Double>> getAverageSessionDuration() {
        log.info("Fetching average session duration");
        Double duration = sessionMetricsService.getAverageSessionDuration();
        return ResponseEntity.ok(Map.of("averageSessionDurationSeconds", duration));
    }

    /**
     * Get total sessions created.
     * GET /api/sessions/total
     */
    @GetMapping("/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Long>> getTotalSessionsCreated() {
        log.info("Fetching total sessions created");
        Long total = sessionMetricsService.getTotalSessionsCreated();
        return ResponseEntity.ok(Map.of("totalSessionsCreated", total));
    }
}
