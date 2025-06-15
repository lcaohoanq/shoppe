package com.lcaohoanq.authservice.domains.token

interface ITokenCleanupService {
    fun cleanupExpiredTokens()
}
