package com.lcaohoanq.authservice.domains.token

import com.lcaohoanq.authservice.repositories.TokenRepository
import jakarta.transaction.Transactional
import mu.KotlinLogging
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class TokenCleanupService(
    private val tokenRepository: TokenRepository
) : ITokenCleanupService {

    private val log = KotlinLogging.logger {}

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    @Async
    override fun cleanupExpiredTokens() {
        try {
            tokenRepository.deleteExpiredTokens(LocalDateTime.now())
        } catch (e: Exception) {
            // Log the exception and handle error
            log.error("Error auto setting Token expired", e.cause)
        }
    }
}
