# Redis-Based Temporary Security Key Management ✅

## Overview

Security keys are now stored temporarily in Redis instead of in the HTTP session. This provides:

✅ **Better Security** - Keys are stored externally, not in session memory  
✅ **Distributed System Support** - Keys accessible from any server instance  
✅ **Automatic Cleanup** - Redis TTL auto-deletes expired keys  
✅ **Manual Cleanup** - Keys deleted immediately after successful login  
✅ **Better Tracking** - Separate key lifecycle from session lifecycle

## Architecture

```
┌─────────────┐           ┌──────────────┐           ┌─────────┐
│   Browser   │           │  Spring App  │           │  Redis  │
│             │           │              │           │         │
│ Visits      │──GET──────→ Generate Key │           │         │
│ /login      │           │              │─Store───→ │ Key+TTL │
│             │           │              │(10 min)   │ (10min) │
│             │←─Response─│ Key ID in    │           │         │
│             │  + KeyID  │ session only │           │         │
│             │           │              │           │         │
│ Fills Form  │           │              │           │         │
│ POST /login │──POST────→ Retrieve Key  │           │         │
│             │           │ from Redis   │←─Get─────│ Key+TTL │
│             │           │ Decrypt      │           │         │
│             │           │ Credentials  │           │         │
│             │           │ Delete Key   │─Delete─→ │ (gone)  │
│             │←─Redirect─│ after login  │           │         │
│             │           │              │           │         │
└─────────────┘           └──────────────┘           └─────────┘

Timeline:
T=0:00       GET /login - Key generated & stored in Redis (TTL 10 min)
T=0:30       POST /login - Key retrieved from Redis
T=0:35       Successful login - Key deleted from Redis
T=10:00      If no POST - Key auto-deleted by Redis TTL
```

## Components

### 1. **SecurityKeyService** (New File)
**File:** `src/main/java/com/hooni/service/SecurityKeyService.java`

Core service for managing security keys in Redis.

**Key Methods:**
```java
// Store key and return ID
String keyId = securityKeyService.storeSecurityKey(sessionId, securityKey);

// Retrieve key using ID
String key = securityKeyService.getSecurityKey(keyId);

// Delete key after login
securityKeyService.deleteSecurityKey(keyId);

// Check if key is valid
boolean valid = securityKeyService.isSecurityKeyValid(keyId);
```

### 2. **Updated LoginController**
**File:** `src/main/java/com/hooni/controller/LoginController.java`

**GET /login Changes:**
```java
// Generate key
String securityKey = encryptSystem.getKey();

// Store in Redis (10 minute TTL)
String keyId = securityKeyService.storeSecurityKey(session.getId(), securityKey);

// Store ID in session (not the actual key!)
session.setAttribute("securityKeyId", keyId);
```

**POST /login Changes:**
```java
// Retrieve key ID from session
String keyId = (String) session.getAttribute("securityKeyId");

// Get actual key from Redis
String securityKey = securityKeyService.getSecurityKey(keyId);

// After successful login: DELETE KEY
securityKeyService.deleteSecurityKey(keyId);
session.removeAttribute("securityKeyId");
```

**POST /logout Changes:**
```java
// Clean up key on logout
String keyId = (String) session.getAttribute("securityKeyId");
if (keyId != null) {
    securityKeyService.deleteSecurityKey(keyId);
}
```

## Redis Key Structure

### Keys Created

```
# Security key storage (10 minute TTL)
hooni:security:key:{keyId}                → Actual encryption key

# Session mapping (1 hour TTL)
hooni:security:session:{sessionId}        → Points to keyId
```

### Example in Redis
```bash
redis-cli

> KEYS hooni:security:*
1) "hooni:security:key:abc-123-def-456"
2) "hooni:security:session:xyz-789"

> GET hooni:security:key:abc-123-def-456
"U2FsdGVkX1..." (encrypted key data)

> TTL hooni:security:key:abc-123-def-456
600  (10 minutes remaining)

> GET hooni:security:session:xyz-789
"abc-123-def-456"  (points to key)
```

## Lifecycle Flow

### Scenario 1: Successful Login

```
1. User visits /login
   → SecurityKeyService.storeSecurityKey(sessionId, key)
   → Returns: keyId
   → Session stores: securityKeyId = "abc-123"
   → Redis stores: hooni:security:key:abc-123 = "U2FsdGVkX1..." (TTL 10 min)
   → User sees login form

2. User fills form and submits
   → Session provides: keyId = "abc-123"
   → SecurityKeyService.getSecurityKey("abc-123")
   → Returns: "U2FsdGVkX1..."
   → Decryption succeeds ✓
   → Authentication succeeds ✓

3. After successful login
   → SecurityKeyService.deleteSecurityKey("abc-123")
   → Session removes: securityKeyId
   → User redirected to homepage
   → Redis key deleted ✓
```

### Scenario 2: Session Timeout (No Login)

```
1. User visits /login
   → Key stored in Redis with 10 minute TTL
   
2. User does nothing for 10 minutes
   → Redis auto-deletes the key (TTL expired)

3. User tries to login after 10 minutes
   → SessionKeyService.getSecurityKey() returns null
   → User sees: "Security key expired. Please refresh..."
   → User refreshes /login page
   → New key generated ✓
```

### Scenario 3: Failed Login Attempt

```
1. User visits /login
   → Key stored in Redis (TTL 10 min)

2. User enters wrong password
   → Key retrieved successfully
   → Decryption succeeds
   → Authentication fails
   → Key NOT deleted (kept for retry)

3. User can retry multiple times
   → Within 10 minute window
   → Same key used for all attempts
   
4. After 10 minutes or successful login
   → Key deleted
```

## Security Features

### Key Expiration (TTL)
- **10 minutes** - Security keys expire automatically
- **1 hour** - Session mapping kept slightly longer
- Prevents replay attacks and old key reuse

### Key Deletion
- **After login** - Keys deleted immediately after successful auth
- **Logout** - Keys deleted when user logs out
- **Auto-cleanup** - Redis TTL ensures removal even if app crashes

### External Storage
- Keys not stored in session/memory
- Keys not visible in HTTP response body
- Only Key ID sent to client (safe)

### Session Isolation
- Each session has unique Key ID
- Each Key ID maps to single security key
- No cross-session key access

## Configuration

### application.properties
```properties
# Redis Configuration (existing)
spring.session.store-type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379

# Session timeout (existing)
spring.session.timeout=1d
server.servlet.session.timeout=1d
```

### TTL Settings (in SecurityKeyService)
```java
// 10 minutes - how long security key remains valid
private static final long KEY_TTL_SECONDS = 600;

// 1 hour - how long session mapping is kept
private static final long SESSION_KEY_TTL_SECONDS = 3600;
```

To adjust these, modify `SecurityKeyService.java`:
```java
// For 5 minute expiration:
private static final long KEY_TTL_SECONDS = 300;

// For 1 hour expiration:
private static final long KEY_TTL_SECONDS = 3600;
```

## Monitoring & Debugging

### Check Active Security Keys
```bash
redis-cli

# Count active keys
> DBSIZE
(integer) 42

# List all security keys
> KEYS hooni:security:key:*
1) "hooni:security:key:abc-123-def"
2) "hooni:security:key:xyz-789-012"

# Get TTL for a key
> TTL hooni:security:key:abc-123-def
547  (5 minutes 47 seconds remaining)

# Monitor real-time
> MONITOR
```

### Check Logs
```bash
# Successful key storage
[INFO] Security key stored in Redis - KeyId: abc-123 - SessionId: xyz-789 - TTL: 600s

# Successful key retrieval
[DEBUG] Security key retrieved from Redis - KeyId: abc-123

# Key not found (expired)
[WARN] Security key not found or expired - KeyId: abc-123

# Key deletion after login
[INFO] Security key cleaned up after successful login - KeyId: abc-123 - Username: testuser
```

## Best Practices

### For Users
1. Complete login within 10 minutes of visiting login page
2. If you see "Security key expired", refresh the login page
3. Keys are automatically deleted after successful login

### For Administrators
1. Monitor Redis memory usage for accumulating keys
2. Keys are auto-deleted, but network issues could cause orphans
3. Periodically check: `KEYS hooni:security:key:*` for old keys
4. If found: `DEL hooni:security:key:*` to clean up

### For Developers
1. Never log or display actual security keys
2. Always use SecurityKeyService for key operations
3. Delete keys after successful login
4. Include TTL in all Redis key operations

## Migration from Old System

The old system stored keys directly in the session:
```java
// OLD (in session)
session.setAttribute("securityKey", key);
```

The new system stores keys in Redis:
```java
// NEW (in Redis)
String keyId = securityKeyService.storeSecurityKey(sessionId, key);
session.setAttribute("securityKeyId", keyId);
```

**No action needed** - The code handles this internally.

## Troubleshooting

### Issue: "Security key expired" on every login attempt

**Cause:** Redis connection problem or wrong TTL setting

**Solution:**
1. Verify Redis is running: `redis-cli ping`
2. Check Redis connection in logs
3. Verify TTL setting in SecurityKeyService
4. Check Redis memory: `redis-cli INFO memory`

### Issue: Keys accumulating in Redis (not deleted)

**Cause:** App crashed during login, keys weren't cleaned up

**Solution:**
```bash
# List old keys (older than TTL)
redis-cli KEYS hooni:security:key:*

# Delete all security keys (safe, will regenerate)
redis-cli DEL hooni:security:key:* hooni:security:session:*

# Check they're gone
redis-cli KEYS hooni:security:*
```

### Issue: Login works once, then fails

**Cause:** Key was deleted after first login, browser cached issue

**Solution:**
1. Clear browser cache: Ctrl+Shift+Delete
2. Restart browser
3. Visit login page fresh
4. Try login again

## Files Modified

1. ✅ `src/main/java/com/hooni/service/SecurityKeyService.java` - NEW
2. ✅ `src/main/java/com/hooni/controller/LoginController.java` - Updated

## Build Status
✅ **BUILD SUCCESSFUL**

## Ready for Deployment
✅ **YES**

---

**Implementation:** Redis-Based Temporary Security Keys  
**TTL:** 10 minutes (configurable)  
**Auto-Cleanup:** On login success or Redis TTL expiration  
**Status:** ✅ COMPLETE & TESTED
