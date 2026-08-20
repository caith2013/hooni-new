# Spring Session with Redis - Distributed System Configuration

## Overview

Your Hooni application now supports **distributed session management** using **Spring Session with Redis**. This enables:

- ✅ **Session sharing** across multiple server instances
- ✅ **Load balancing** without session loss
- ✅ **High availability** - sessions survive server restarts
- ✅ **Automatic cleanup** of expired sessions
- ✅ **Thread-safe** session access across distributed nodes

## Architecture

```
┌─────────────────┐     ┌─────────────────┐     ┌─────────────────┐
│   Server 1      │     │   Server 2      │     │   Server 3      │
│  (Port 8080)    │     │  (Port 8081)    │     │  (Port 8082)    │
└────────┬────────┘     └────────┬────────┘     └────────┬────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                         ┌───────▼────────┐
                         │   Redis Cache  │
                         │  (localhost:   │
                         │    6379)       │
                         └────────────────┘
```

## Components Added

### 1. **SessionConfig.java** (`src/main/java/com/hooni/config/SessionConfig.java`)
- Enables Redis-backed HTTP sessions
- Configures session timeout (30 minutes default)
- Sets up header-based session ID resolution for REST clients

### 2. **SessionUtils.java** (`src/main/java/com/hooni/util/SessionUtils.java`)
- Utility methods for session operations
- Methods:
  - `storeUserInSession()` - Store user details
  - `getUserFromSession()` - Retrieve username
  - `setSessionAttribute()` - Store custom attributes
  - `getSessionAttribute()` - Retrieve custom attributes
  - `clearUserSession()` - Clean up user data

### 3. **Updated Controllers** - All 9 controllers now support sessions:
- LoginController
- HomeController
- NewsController
- ProductController
- FoodController
- AdsController
- ShareController
- RegisterController
- FooterController

## Session Data Stored

Each session stores:

```
{
  "loggedInUser": "username",                    // From AuthenticationPrincipal
  "userDetails": UserDetails object,            // Security principal
  "currentPage": "products/food/ads/...",       // Current page being viewed
  "viewedProductId": 123,                       // Last viewed product
  "viewedFoodId": 456,                          // Last viewed food
  "viewedNewsId": 789,                          // Last viewed news
  "lastSearchCategory": "electronics",          // Last search filters
  "lastSearchBrand": "Sony",                    // Last search filters
  "mealPlanModified": true,                     // Meal plan change flag
  "favoritesChanged": true,                     // Favorites change flag
  "sessionId": "abc123def...",                  // Unique session ID
  "lastVisited": "op_value",                    // Last visited operation
  "lastVisitTime": 1691234567890,              // Timestamp of last visit
}
```

## Configuration

### application.properties

```properties
# Spring Session Configuration
spring.session.store-type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.data.redis.password=
spring.data.redis.timeout=60000ms
spring.session.redis.namespace=hooni:session
```

### Customize for Your Environment

**Local Development:**
```properties
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

**Docker/Container:**
```properties
spring.data.redis.host=redis-service
spring.data.redis.port=6379
```

**Production with Auth:**
```properties
spring.data.redis.host=redis.example.com
spring.data.redis.port=6379
spring.data.redis.password=your_secure_password
```

## Setting Up Redis

### Option 1: Local Installation (Development)

**Windows (WSL or Docker Desktop):**
```bash
docker run -d -p 6379:6379 --name redis redis:7-alpine
```

**Linux:**
```bash
sudo apt-get install redis-server
sudo service redis-server start
```

**macOS:**
```bash
brew install redis
redis-server
```

### Option 2: Docker Compose (Multi-container)

Create `docker-compose.yml`:

```yaml
version: '3.8'
services:
  redis:
    image: redis:7-alpine
    ports:
      - "6379:6379"
    volumes:
      - redis-data:/data
    command: redis-server --appendonly yes

  hooni-app-1:
    build: .
    ports:
      - "8080:8080"
    environment:
      SPRING_DATA_REDIS_HOST: redis
    depends_on:
      - redis

  hooni-app-2:
    build: .
    ports:
      - "8081:8080"
    environment:
      SPRING_DATA_REDIS_HOST: redis
    depends_on:
      - redis

volumes:
  redis-data:
```

Run:
```bash
docker-compose up -d
```

### Option 3: Kubernetes Deployment

Create `redis-deployment.yaml`:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: redis
spec:
  replicas: 1
  selector:
    matchLabels:
      app: redis
  template:
    metadata:
      labels:
        app: redis
    spec:
      containers:
      - name: redis
        image: redis:7-alpine
        ports:
        - containerPort: 6379
        volumeMounts:
        - name: redis-data
          mountPath: /data
      volumes:
      - name: redis-data
        emptyDir: {}
---
apiVersion: v1
kind: Service
metadata:
  name: redis-service
spec:
  ports:
  - port: 6379
  selector:
    app: redis
```

## Usage in Controllers

All controllers now accept `HttpSession` parameter:

```java
@GetMapping("/product")
public String productList(
    @RequestParam(name = "pid", required = false) Long pid,
    HttpSession session,  // Spring Session injects this
    Model model) {
    
    // Store data for distributed access
    SessionUtils.setSessionAttribute(session, "viewedProductId", pid);
    SessionUtils.setSessionAttribute(session, "currentPage", "products");
    
    // Retrieve data from any server instance
    Long previousProductId = (Long) SessionUtils.getSessionAttribute(session, "viewedProductId");
    
    // ... rest of controller logic
}
```

## Session Lifecycle

### 1. **Session Creation**
```
User Request → Server 1 → Creates Session → Stores in Redis
```

### 2. **Session Sharing (Load Balancing)**
```
Next Request (via Server 2) → Retrieves Session from Redis → User context available
```

### 3. **Session Expiration**
```
30 minutes of inactivity → Redis auto-deletes session → User logs out
```

## Monitoring Sessions

### Redis CLI

```bash
# Connect to Redis
redis-cli

# List all session keys
KEYS hooni:session:*

# View session data
GET hooni:session:abc123def...

# Monitor session activity
MONITOR
```

### Spring Boot Actuator (optional)

Add to `application.properties`:
```properties
management.endpoints.web.exposure.include=health,metrics
```

Access: `http://localhost:8080/actuator/health`

## Troubleshooting

### Sessions Not Persisting
1. Verify Redis is running: `redis-cli ping` (should return `PONG`)
2. Check connection settings in `application.properties`
3. Verify no firewall blocking port 6379

### Multiple Servers, Different Sessions
1. Ensure all servers connect to **same Redis instance**
2. Check `spring.data.redis.host` is identical across all servers
3. Use Redis Sentinel/Cluster for HA

### Performance Issues
1. Monitor Redis memory: `redis-cli INFO memory`
2. Tune session timeout: `@EnableRedisHttpSession(maxInactiveIntervalInSeconds=...)`
3. Use Redis connection pooling (automatic with Spring Boot)

## Security Considerations

1. **Password Protection:**
   ```properties
   spring.data.redis.password=your_strong_password
   ```

2. **Network Security:**
   - Use private network for Redis
   - Firewall port 6379 to only application servers

3. **Session Serialization:**
   - Spring Session serializes objects using JDK serialization
   - Only store serializable data in sessions

4. **HTTPS Enforcement:**
   - Add to `application.properties`:
   ```properties
   server.servlet.session.cookie.secure=true
   server.servlet.session.cookie.http-only=true
   server.servlet.session.cookie.same-site=strict
   ```

## Performance Tuning

```properties
# Session timeout (default: 30 minutes)
spring.session.timeout=1800s

# Connection pool settings
spring.data.redis.jedis.pool.max-active=8
spring.data.redis.jedis.pool.max-idle=8
spring.data.redis.jedis.pool.min-idle=0
spring.data.redis.jedis.pool.timeout=2000ms
```

## Next Steps

1. ✅ Install and start Redis
2. ✅ Update `application.properties` with your Redis host/port
3. ✅ Run: `mvn clean install`
4. ✅ Start multiple server instances
5. ✅ Test session sharing by:
   - Login on Server 1 (port 8080)
   - Request page on Server 2 (port 8081) → session should be recognized
   - Verify user remains logged in

## Documentation

- [Spring Session](https://spring.io/projects/spring-session)
- [Redis Documentation](https://redis.io/documentation)
- [Spring Boot Redis](https://spring.io/guides/gs/messaging-redis/)
