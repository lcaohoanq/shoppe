package com.lcaohoanq.gateway.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key

@Component
class JwtTokenProvider {

    @Value("\${jwt.secretKey}")
    private lateinit var secretKey: String

    @Value("\${jwt.expiration-refresh-token}")
    private var expirationRefreshToken: Int = 0

    @Value("\${jwt.expiration}")
    private var expirationMs: Long = 0

    private val signingKey: Key by lazy {
        Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    fun extractEmail(token: String): String {
        return getClaims(token).subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            getClaims(token)
            true
        } catch (e: JwtException) {
            false
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun getClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}