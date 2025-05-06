package com.lcaohoanq.gateway.service

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap

@Service
class TokenBlacklistService {
    // Store blacklisted tokens with their expiration time
    private val blacklistedTokens = ConcurrentHashMap<String, Instant>()

    // Add a token to the blacklist with its expiration time
    fun blacklistToken(token: String, expirationTime: Instant) {
        blacklistedTokens[token] = expirationTime
    }

    // Check if a token is blacklisted
    fun isBlacklisted(token: String): Boolean {
        val expiration = blacklistedTokens[token] ?: return false

        // If the token has expired, remove it from the blacklist
        if (Instant.now().isAfter(expiration)) {
            blacklistedTokens.remove(token)
            return false
        }

        return true
    }

    // Cleanup method to remove expired tokens (can be scheduled)
    fun cleanupExpiredTokens() {
        val now = Instant.now()
        blacklistedTokens.entries.removeIf { (_, expiration) -> now.isAfter(expiration) }
    }
}
