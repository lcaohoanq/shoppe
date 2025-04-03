package com.lcaohoanq.ktservice.domain.token

import com.lcaohoanq.common.utils.Identifiable
import com.lcaohoanq.ktservice.entities.Token
import com.lcaohoanq.ktservice.entities.User
import com.lcaohoanq.ktservice.repositories.TokenRepository
import com.lcaohoanq.ktservice.repositories.UserRepository
import com.lcaohoanq.ktservice.component.JwtTokenUtils
import com.lcaohoanq.ktservice.domain.user.IUserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*


@Service
class TokenService(
    private val userService: IUserService,
    private val userRepository: UserRepository,
    private val tokenRepository: TokenRepository, // Note: Changed from UserRepository
    private val jwtTokenUtil: JwtTokenUtils,
    private val request: HttpServletRequest
) : ITokenService, Identifiable {

    companion object {
        private const val MAX_TOKENS = 3
    }

    @Value("\${jwt.expiration}")
    private val expiration: Long = 0

    @Value("\${jwt.expiration-refresh-token}")
    private val expirationRefreshToken: Long = 0

    @Transactional
    override fun addToken(userId: Long, token: String): Token {
        val user = userRepository.findById(userId)
            .orElseThrow { com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException("User not found") }

        val userTokens = tokenRepository.findByUserId(userId)

        // Manage token limit
        if (userTokens.size >= MAX_TOKENS) {
            val tokenToDelete = userTokens.let { tokens ->
                when {
                    tokens.any { !it.isMobile } -> tokens.first { !it.isMobile }
                    else -> tokens.first()
                }
            }
            tokenRepository.delete(tokenToDelete)
        }

        val expirationDateTime = LocalDateTime.now().plusSeconds(expiration)

        val isMobileDevice = isMobileDevice(request.getHeader("User-Agent"))

        return Token(
            user = user,
            token = token,
            revoked = false,
            expired = false,
            tokenType = "Bearer",
            expirationDate = expirationDateTime,
            isMobile = isMobileDevice,
            refreshToken = UUID.randomUUID().toString(),
            refreshExpirationDate = LocalDateTime.now().plusSeconds(expirationRefreshToken)
        ).also { tokenRepository.save(it) }
    }

    @Transactional
    override fun refreshToken(refreshToken: String, user: User): Token {
        val existingToken = tokenRepository.findByRefreshToken(refreshToken) ?: throw com.lcaohoanq.ktservice.exceptions.TokenNotFoundException(
            "Refresh token does not exist"
        )

        // Check token expiration
        require(existingToken.refreshExpirationDate.isAfter(LocalDateTime.now())) {
            throw com.lcaohoanq.ktservice.exceptions.ExpiredTokenException("Refresh token is expired")
        }

        return existingToken.apply {
            val newToken = jwtTokenUtil.generateToken(user)
            token = newToken
            expirationDate = LocalDateTime.now().plusSeconds(expiration)
//            refreshToken = UUID.randomUUID().toString()
            refreshExpirationDate = LocalDateTime.now().plusSeconds(expirationRefreshToken)
        }.also { tokenRepository.save(it) }
    }

    override fun deleteToken(token: String, user: User) {
        val existingToken = tokenRepository.findByToken(token) ?: throw com.lcaohoanq.ktservice.exceptions.TokenNotFoundException(
            "Token does not exist"
        )

        // Validate token ownership and status
        require(!existingToken.revoked) {
            throw com.lcaohoanq.ktservice.exceptions.TokenNotFoundException("Token has been revoked")
        }
        require(existingToken.user.id == user.id) {
            throw com.lcaohoanq.ktservice.exceptions.TokenNotFoundException("Token does not exist")
        }

        existingToken.revoked = true
        tokenRepository.save(existingToken)
    }

    override fun findUserByToken(token: String): Token =
        tokenRepository.findByToken(token) ?: throw com.lcaohoanq.ktservice.exceptions.TokenNotFoundException(
            "Token does not exist"
        )
}