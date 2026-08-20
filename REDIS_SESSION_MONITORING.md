# Redis Session Monitoring & Metrics - Advanced Setup

## Overview

Your Hooni application now includes **advanced Redis session tracking with comprehensive monitoring and metrics**. This enables real-time tracking of user sessions across your distributed system with detailed analytics.

## New Features Added

### 1. **SessionMetricsService** (`src/main/java/com/hooni/service/SessionMetricsService.java`)
Core service for tracking and monitoring session metrics.

**Key Methods:**
- `recordSessionCreated(sessionId, username)` - Track session creation
- `recordSessionActivity(sessionId, activityType, details)` - Track user actions
- `recordSessionTerminated(sessionId)` - Record session end
- `getSessionStatistics()` - Retrieve overall statistics
- `getActiveSessionCount()` - Get current active sessions
- `getAllActiveSessions()` - List all active sessions with details
- `getAverageSessionDuration()` - Calculate average session length
- `getTotalSessionsCreated()` - Total sessions since startup

### 2. **AdvancedSessionUtils** (`src/main/java/com/hooni/util/AdvancedSessionUtils.java`)
Extended utility class for tracking and analyzing sessions.

**Key Methods:**
- `trackLoginEvent()` - Initialize comprehensive login tracking
- `trackActivity()` - Count and timestamp user activities
- `getSessionDurationSeconds()` - Get elapsed session time
- `isSessionIdle()` - Check for inactive sessions
- `addSessionTag()` - Tag sessions for categorization
- `getSessionInfoSummary()` - Get human-readable session info

### 3. **SessionMonitoringController** (`src/main/java/com/hooni/controller/SessionMonitoringController.java`)
REST API endpoints for monitoring session metrics (Admin only).

**Available Endpoints:**

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/api/sessions/statistics` | GET | Overall session statistics |
| `/api/sessions/active/count` | GET | Count of active sessions |
| `/api/sessions/active` | GET | List all active sessions |
| `/api/sessions/{sessionId}/metrics` | GET | Specific session metrics |
| `/api/sessions/average-duration` | GET | Average session duration |
| `/api/sessions/total` | GET | Total sessions created |

### 4. **SessionMetricsInterceptor** (`src/main/java/com/hooni/config/SessionMetricsInterceptor.java`)
Automatically captures session metrics for every request.

**Tracks:**
- Session creation on first request
- Activity type and details
- Request path and method
- Session termination

## Redis Keys Structure

```
hooni:metrics:sessions:{sessionId}          - Session metadata (Hash)
hooni:metrics:active_users                  - Active session count (String)
hooni:metrics:total_sessions                - Total sessions created (String)
hooni:metrics:session_duration              - Session durations list (List)
```

## Session Attributes Stored

### Basic Session Info
```
- sessionId: Unique session identifier
- username: Authenticated user
- createdAt: Session creation timestamp (ms)
- lastActivity: Last activity timestamp (ms)
- status: "active" or "terminated"
```

### Activity Tracking
```
- lastActivityType: Type of last activity
- lastActivityDetails: Details of last activity
- duration: Total session duration (ms)
- terminatedAt: When session ended
```

### Advanced Tracking (via AdvancedSessionUtils)
```
- login_time: User login timestamp
- activity_count: Total activities in session
- device_info: Device information
- ip_address: User IP address
- user_agent: Browser user agent
- session_tags: Custom categorization tags
```

## Usage Examples

### 1. Basic Session Tracking in Controllers

```java
@PostMapping("/login")
public String login(@RequestBody LoginRequest request, 
                   HttpServletRequest httpRequest,
                   HttpSession session,
                   Model model) {
    // Authenticate user...
    UserDetails principal = getUserDetails(request.getUsername());
    
    // Track login with IP and user agent
    String ip = httpRequest.getRemoteAddr();
    String userAgent = httpRequest.getHeader("User-Agent");
    AdvancedSessionUtils.trackLoginEvent(session, principal, ip, userAgent);
    
    return "redirect:/home";
}
```

### 2. Track User Activities

```java
@GetMapping("/product")
public String productList(HttpSession session, Model model) {
    // Track activity
    AdvancedSessionUtils.trackActivity(session, "VIEW_PRODUCTS");
    
    // Get session info
    Long duration = AdvancedSessionUtils.getSessionDurationSeconds(session);
    Integer activityCount = AdvancedSessionUtils.getActivityCount(session);
    
    model.addAttribute("sessionDuration", duration);
    model.addAttribute("activityCount", activityCount);
    
    return "products";
}
```

### 3. Check Session Idle Status

```java
public void checkIdleSessions() {
    HttpSession session = // Get session from context
    
    if (AdvancedSessionUtils.isSessionIdle(session, 30)) {
        log.warn("Session idle for 30+ minutes: " + session.getId());
        // Take action: warn user, invalidate session, etc.
    }
}
```

### 4. Tag Sessions for Analysis

```java
// Tag session for premium user
AdvancedSessionUtils.addSessionTag(session, "premium_user");

// Tag for specific category
AdvancedSessionUtils.addSessionTag(session, "bulk_buyer");

// Get all tags
Set<String> tags = AdvancedSessionUtils.getSessionTags(session);
```

### 5. Query Session Metrics (Admin)

```java
// In admin controller or service
SessionStatistics stats = sessionMetricsService.getSessionStatistics();

log.info("Active sessions: {}", stats.getActiveSessionsCount());
log.info("Total sessions created: {}", stats.getTotalSessionsCreated());
log.info("Average duration: {}s", stats.getAverageSessionDurationSeconds());
```

## Monitoring Endpoints

### Example: Check Overall Session Health

```bash
curl -H "Authorization: Bearer <ADMIN_TOKEN>" \
  http://localhost:8080/api/sessions/statistics

# Response:
{
  "activeSessionsCount": 42,
  "totalSessionsCreated": 1250,
  "averageSessionDurationSeconds": 1843.5,
  "timestamp": "2025-08-18T16:30:00"
}
```

### Example: Get Active Sessions

```bash
curl -H "Authorization: Bearer <ADMIN_TOKEN>" \
  http://localhost:8080/api/sessions/active

# Response: Array of active sessions with user, IP, duration, etc.
```

### Example: Get Specific Session Details

```bash
curl -H "Authorization: Bearer <ADMIN_TOKEN>" \
  http://localhost:8080/api/sessions/abc123/metrics

# Response: Session metadata, activity, timeline
```

## Dashboard Integration

You can build an admin dashboard using these endpoints:

```html
<div class="session-metrics">
  <h2>Session Metrics</h2>
  
  <div class="metric">
    <label>Active Sessions:</label>
    <span id="active-count">-</span>
  </div>
  
  <div class="metric">
    <label>Avg Duration:</label>
    <span id="avg-duration">-</span>
  </div>
  
  <div class="metric">
    <label>Total Sessions:</label>
    <span id="total-count">-</span>
  </div>
</div>

<script>
setInterval(() => {
  fetch('/api/sessions/statistics')
    .then(r => r.json())
    .then(data => {
      document.getElementById('active-count').textContent = data.activeSessionsCount;
      document.getElementById('avg-duration').textContent = 
        (data.averageSessionDurationSeconds / 60).toFixed(1) + ' min';
      document.getElementById('total-count').textContent = data.totalSessionsCreated;
    });
}, 5000); // Update every 5 seconds
</script>
```

## Security Considerations

1. **Admin-Only Access**: All monitoring endpoints require `@PreAuthorize("hasRole('ADMIN')")`
2. **Sensitive Data**: Don't expose raw session IDs or user details publicly
3. **Rate Limiting**: Consider adding rate limits to monitoring endpoints
4. **Audit Logging**: Enable logging for monitoring endpoint access

## Performance Tuning

### Redis Memory Optimization

```properties
# In application.properties

# Session TTL (expires old sessions automatically)
spring.session.timeout=1d

# Redis connection pool settings
spring.data.redis.jedis.pool.max-active=16
spring.data.redis.jedis.pool.max-idle=8
spring.data.redis.jedis.pool.min-idle=0
```

### Monitor Redis Memory

```bash
# Connect to Redis CLI
redis-cli

# Check memory usage
INFO memory

# Check metrics keys
KEYS hooni:metrics:*
DBSIZE
```

### Clean Up Old Metrics

```bash
# Redis automatically cleans metrics older than 1 day
# To manually clear old session data:
redis-cli DEL hooni:metrics:sessions:*
```

## Troubleshooting

### Metrics Not Recording

1. Verify Redis is running: `redis-cli ping`
2. Check `SessionMetricsInterceptor` is registered in `ResourceConfig`
3. Enable DEBUG logging:
   ```properties
   logging.level.com.hooni.service.SessionMetricsService=DEBUG
   logging.level.com.hooni.config.SessionMetricsInterceptor=DEBUG
   ```

### Missing Session Data

1. Sessions have 1-day TTL in Redis - data expires automatically
2. Confirm `spring.session.timeout=1d` in `application.properties`
3. Check Redis connection: `redis-cli GET hooni:session:*`

### High Memory Usage

1. Reduce `maxInactiveIntervalInSeconds` in `SessionConfig`
2. Increase session timeout cleanup frequency
3. Monitor with `redis-cli INFO memory`

## Next Steps

1. ✅ Deploy enhanced session monitoring to your environment
2. ✅ Build admin dashboard using provided endpoints
3. ✅ Set up alerts for high session counts or long durations
4. ✅ Integrate with logging/monitoring tools (ELK, DataDog, etc.)
5. ✅ Monitor Redis memory and performance

## Integration with Existing Code

The implementation automatically hooks into your existing:
- **LoginController**: Sessions tracked on login
- **All Controllers**: Activity tracked on every request
- **Security**: Uses existing Spring Security principals
- **Redis**: Uses existing Redis connection pool

No changes needed to existing controller code - tracking is automatic via the interceptor.

## References

- [Spring Session Documentation](https://spring.io/projects/spring-session)
- [Redis Documentation](https://redis.io/docs)
- [Spring Data Redis](https://spring.io/projects/spring-data-redis)

---

**Last Updated:** 2025-08-18  
**Version:** 1.0 (Redis Session Monitoring & Metrics)
