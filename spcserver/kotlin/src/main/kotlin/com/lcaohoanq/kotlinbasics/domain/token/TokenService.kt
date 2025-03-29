package com.lcaohoanq.kotlinbasics.domain.token

import com.lcaohoanq.kotlinbasics.component.JwtTokenUtils
import com.lcaohoanq.kotlinbasics.domain.user.IUserService
import com.lcaohoanq.kotlinbasics.domain.user.User
import com.lcaohoanq.kotlinbasics.exceptions.ExpiredTokenException
import com.lcaohoanq.kotlinbasics.exceptions.TokenNotFoundException
import com.lcaohoanq.kotlinbasics.exceptions.base.DataNotFoundException
import com.lcaohoanq.kotlinbasics.repository.TokenRepository
import com.lcaohoanq.kotlinbasics.repository.UserRepository
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
) : ITokenService {

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
            .orElseThrow { DataNotFoundException("User not found") }

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

        val isMobileDevice = request.getHeader("User-Agent")?.contains("Mobile") ?: false

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
        val existingToken = tokenRepository.findByRefreshToken(refreshToken) ?: throw TokenNotFoundException("Refresh token does not exist")

        // Check token expiration
        require(existingToken.refreshExpirationDate.isAfter(LocalDateTime.now())) {
            throw ExpiredTokenException("Refresh token is expired")
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
        val existingToken = tokenRepository.findByToken(token) ?: throw TokenNotFoundException("Token does not exist")

        // Validate token ownership and status
        require(!existingToken.revoked) {
            throw TokenNotFoundException("Token has been revoked")
        }
        require(existingToken.user.id == user.id) {
            throw TokenNotFoundException("Token does not exist")
        }

        existingToken.revoked = true
        tokenRepository.save(existingToken)
    }

    override fun findUserByToken(token: String): Token =
        tokenRepository.findByToken(token) ?: throw TokenNotFoundException("Token does not exist")
}