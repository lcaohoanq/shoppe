# Rate Limiting Implementation Notes

## Overview
This document explains our rate limiting implementation using Bucket4j in our Spring Cloud Gateway application. Rate limiting helps protect our services from abuse and ensures fair resource distribution among clients.

## How Our Rate Limiter Works

### The Bucket Algorithm Explained
We use the token bucket algorithm (implemented via Bucket4j) which works as follows:

1. Each client (identified by IP address + path) gets a "bucket" with a fixed capacity of tokens
2. When a bucket is created, it starts with the maximum number of tokens (full capacity)
3. Each request consumes one token from the bucket
4. Tokens are automatically refilled at a constant rate (e.g., 1 token per second)
5. If a request arrives and no tokens are available, it's rejected with HTTP 429 (Too Many Requests)

### Key Components in Our Implementation

#### 1. Rate Limit Rules Configuration
```kotlin
data class RateLimit(val capacity: Int, val refillPerSec: Int)

val rateLimitRules: Map<Regex, RateLimit> = mapOf(
    Regex("^/api/v1/auth/login") to RateLimit(3, 1), // Strict: 3 requests max, refills at 1/sec
    Regex("^/api/v1/products") to RateLimit(50, 20), // Moderate: 50 requests max, refills at 20/sec
    Regex("^/api/v1/cart") to RateLimit(20, 5),      // 20 requests max, refills at 5/sec
)
```

#### 2. Bucket Creation for Specific Limits
```kotlin
private fun getBucketWithLimit(capacity: Int, refillPerSecond: Int): Bucket {
    return Bucket.builder()
        .addLimit(
            Bandwidth.classic(
                capacity.toLong(),
                Refill.greedy(refillPerSecond.toLong(), Duration.ofSeconds(1))
            )
        )
        .build()
}
```

#### 3. Client Identification
```kotlin
// Client Identification: Get client IP - properly extract from X-Forwarded-For header
val clientIp = request.headers.getFirst("X-Forwarded-For")
    ?: request.remoteAddress?.address?.hostAddress
    ?: "unknown"

val bucketKey = "$clientIp:$path"
```

## Implementation Details

### The Correct Rate Limiting Logic

```kotlin
// Get current available tokens before consumption
val availableTokens = bucket.availableTokens

// Try to consume a token - returns true if successful, false if no tokens available
val consumed = bucket.tryConsume(1)

return if (consumed) {
    // Request allowed, proceed with request processing
    log.info("✅ Request allowed, remaining: ${availableTokens - 1}")
    chain.filter(exchange)
} else {
    // Rate limit exceeded, return 429 response
    log.warn("❌ Rate limit exceeded for IP: $clientIp")
    exchange.response.statusCode = HttpStatus.TOO_MANY_REQUESTS
    exchange.response.setComplete()
}
```

### Important Implementation Notes

1. **Direct Token Consumption Check**: We use `bucket.tryConsume(1)` which returns a boolean indicating if the request can proceed. This is more reliable than checking remaining tokens.

2. **Available Tokens Tracking**: We retrieve `availableTokens` before consumption to accurately log how many tokens remain after the current request.

3. **Bucket Key**: We create a unique bucket for each combination of client IP and request path, allowing for per-endpoint rate limiting.

4. **Configurable Limits**: Different endpoints can have different rate limits through the `rateLimitRules` configuration.

## Avoiding Common Pitfalls

1. **Issue with `tryConsumeAndReturnRemaining`**: Using `bucket.tryConsumeAndReturnRemaining(1)` and only checking `probe.isConsumed` can be misleading. When tokens are zero, the operation might succeed but leave zero tokens, which doesn't properly reject the next request.

2. **Before/After Token Count**: Always check if consumption was successful with `tryConsume(1)` rather than relying on remaining token count.

3. **Proper Bucket Creation**: Ensure your bucket's capacity and refill rate match your intended rate limit policy.

## Testing Rate Limiting

When testing rate limiting, ensure:

1. Send requests faster than the refill rate to properly test the limit
2. Verify that request number N+1 (where N is your capacity) gets rejected with HTTP 429
3. Wait for token refill and verify that requests are allowed again

Example test scenario for a rate limit of 3 requests with refill of 1/sec:
- Send 3 requests rapidly - all should succeed (200 OK)
- Send a 4th request immediately - should be rejected (429 Too Many Requests)
- Wait 1 second (1 token refilled) - next request should succeed (200 OK)