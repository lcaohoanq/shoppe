package com.lcaohoanq.authservice.components

import com.lcaohoanq.authservice.domains.token.Token
import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.authservice.repositories.TokenRepository
import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.io.Encoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.security.SecureRandom
import java.util.*
import java.util.function.Function

@Component
class JwtTokenUtils(
    private val tokenRepository: TokenRepository
) {

    @Value("\${jwt.expiration}")
    private val expiration = 0 //save to an environment variable

    @Value("\${jwt.expiration-refresh-token}")
    private val expirationRefreshToken = 0

    @Value("\${jwt.secretKey}")
    private val secretKey: String? = null

    //    private final TokenRepository tokenRepository;
    @Throws(Exception::class)
    fun generateToken(user: User): String {
        //properties => claims
        val claims: MutableMap<String, Any> = HashMap()
        //this.generateSecretKey();
        claims["email"] = user.email
        claims["userId"] = user.id!!.toString()
        try {
            //how to extract claims from this ?
            return Jwts.builder()
                .setClaims(claims) //how to extract claims from this ?
                .setSubject(user.email)
                .setExpiration(Date(System.currentTimeMillis() + expiration * 1000L))
                .signWith(signInKey, SignatureAlgorithm.HS256)
                .compact()
        } catch (e: Exception) {
            //you can "inject" Logger, instead System.out.println
            throw com.lcaohoanq.common.exceptions.InvalidParamException("Cannot create jwt token, error: " + e.message)
            //return null;
        }
    }

    private val signInKey: Key
        get() {
            val bytes: ByteArray = Decoders.BASE64.decode(secretKey)
            //Keys.hmacShaKeyFor(Decoders.BASE64.decode("TaqlmGv1iEDMRiFp/pHuID1+T84IABfuA0xXh4GhiUI="));
            return Keys.hmacShaKeyFor(bytes)
        }

    private fun generateSecretKey(): String {
        val random = SecureRandom()
        val keyBytes = ByteArray(32) // 256-bit key
        random.nextBytes(keyBytes)
        return Encoders.BASE64.encode(keyBytes)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(signInKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims: Claims = this.extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    //check expiration
    fun isTokenExpired(token: String): Boolean {
        val expirationDate = this.extractClaim<Date>(
            token
        ) { obj: Claims -> obj.expiration }
        return expirationDate.before(Date())
    }

    fun extractEmail(token: String): String {
        return extractClaim(
            token
        ) { obj: Claims -> obj.subject }
    }

    fun validateToken(token: String, userDetails: User): Boolean {
        try {
            val email = extractEmail(token)
            val existingToken: Token =
                tokenRepository.findByToken(token) ?: throw com.lcaohoanq.common.exceptions.JwtAuthenticationException(
                    "Token is invalid"
                )

            // Check token existence and revocation
            if (existingToken.revoked) {
                throw com.lcaohoanq.common.exceptions.JwtAuthenticationException("Token is invalid or has been revoked")
            }

            // Check token matches user
            if (email != userDetails.getUsername()) {
                throw com.lcaohoanq.common.exceptions.JwtAuthenticationException("Token does not match user")
            }

            // Check expiration
            if (isTokenExpired(token)) {
                throw ExpiredJwtException(null, null, "Token has expired")
            }

            return true
        } catch (e: ExpiredJwtException) {
            throw com.lcaohoanq.common.exceptions.JwtAuthenticationException("JWT token has expired")
        } catch (e: MalformedJwtException) {
            throw com.lcaohoanq.common.exceptions.JwtAuthenticationException("Invalid JWT token format")
        } catch (e: UnsupportedJwtException) {
            throw com.lcaohoanq.common.exceptions.JwtAuthenticationException("Unsupported JWT token")
        } catch (e: IllegalArgumentException) {
            throw com.lcaohoanq.common.exceptions.JwtAuthenticationException("JWT claims string is empty")
        }
    }

    fun generate2FAToken(user: User): String {
        val claims = HashMap<String, Any>()
        claims["is2FAToken"] = true

        return Jwts.builder()
            .setClaims(claims)
            .setSubject(user.email)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 minutes
            .signWith(signInKey, SignatureAlgorithm.HS256)
            .compact()
    }

}
