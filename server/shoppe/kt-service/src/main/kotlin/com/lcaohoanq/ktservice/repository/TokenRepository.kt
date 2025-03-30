package com.lcaohoanq.ktservice.repository

import com.lcaohoanq.ktservice.domain.token.Token
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDateTime
import java.util.*


interface TokenRepository : JpaRepository<Token, UUID> {

    fun findByToken(token: String): Token?

    fun findByRefreshToken(refreshToken: String): Token?

    fun findByUserId(user_id: Long): List<Token>

    @Modifying
    @Transactional
    @Query("DELETE FROM Token t WHERE t.expirationDate < :now OR t.expired = true")
    fun deleteExpiredTokens(@Param("now") now: LocalDateTime)

}