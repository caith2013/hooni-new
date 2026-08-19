package com.hooni.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Data class for session statistics.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionStatistics {
    private Long activeSessionsCount;
    private Long totalSessionsCreated;
    private Double averageSessionDurationSeconds;
    private LocalDateTime timestamp;
}
