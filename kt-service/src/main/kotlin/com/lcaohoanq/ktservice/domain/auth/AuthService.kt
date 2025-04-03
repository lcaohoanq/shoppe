package com.lcaohoanq.ktservice.domain.auth

import com.lcaohoanq.ktservice.dto.TokenPort
import com.lcaohoanq.ktservice.dto.UserPort.UserResponse
import com.lcaohoanq.ktservice.component.JwtTokenUtils
import com.lcaohoanq.ktservice.domain.token.TokenService
import com.lcaohoanq.ktservice.domain.user.IUserService
import com.lcaohoanq.ktservice.entities.Token
import com.lcaohoanq.ktservice.entities.User
import com.lcaohoanq.ktservice.extension.toTokenResponse
import com.lcaohoanq.ktservice.extension.toUserResponse
import com.lcaohoanq.ktservice.repositories.UserRepository
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class AuthService(
    private val userService: IUserService,
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtils: JwtTokenUtils,
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder
) : IAuthService{

    private val log = KotlinLogging.logger {}

    override fun login(account: AuthPort.AuthRequest): AuthPort.AuthResponse {
        val (email, rawPassword) = account

        // Step 1: Retrieve the user by email
        val existUser = userRepository.findByEmail(email)
            ?: throw BadCredentialsException("Wrong email or password")

        // Step 2: Use the password encoder to check if the password is correct
        if (!passwordEncoder.matches(rawPassword, existUser.password)) {
            throw BadCredentialsException("Wrong email or password")
        }

        // Step 3: Create an authentication token
        val authenticationToken =
            UsernamePasswordAuthenticationToken(email, rawPassword, existUser.authorities)

        // Step 4: Authenticate the token
        val authentication = authenticationManager.authenticate(authenticationToken)

        // Step 5: Generate JWT token
        val token = jwtTokenUtils.generateToken(existUser)

        // Get the user details from the generated token
        val userDetail = getUserDetailsFromToken(token)

        // Add the token to the token service
        val jwtToken: Token = tokenService.addToken(
            userDetail.id,
            token
        )

        log.info("New user logged in successfully");

        // Return the authentication response
        return AuthPort.AuthResponse(
            token = jwtToken.toTokenResponse()
        )
    }


    override fun register(newAccount: AuthPort.SignUpReq) {

        if (userRepository.existsByEmail(newAccount.email)) {
            throw DataIntegrityViolationException("Email is already taken")
        }

        userRepository.save(
            User(
                email = newAccount.email,
                hashedPassword = passwordEncoder.encode(newAccount.password),
                role = User.Role.MEMBER,
                userName = "",
                address = "",
                phone = "",
                avatar = "https://api.dicebear.com/9.x/adventurer/svg?seed=${newAccount.email}",
            )
        )
    }

    override fun getUserDetailsFromToken(token: String): UserResponse {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw com.lcaohoanq.ktservice.exceptions.ExpiredTokenException("Token is expired")
        }
        val email = jwtTokenUtils.extractEmail(token)
        val user = userRepository.findByEmail(email)
        if (user != null) {
            return user.toUserResponse()
        } else {
            throw com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException("User not found")
        }

    }

    override fun getCurrentAuthenticatedUser(): User {
        return userService.findByEmail(
            SecurityContextHolder.getContext().authentication.name
        ) ?: throw com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException("User not found")
    }

    override fun refreshToken(refreshTokenDTO: TokenPort.RefreshTokenDTO): AuthPort.AuthResponse {
        val userDetail = userService.getUserDetailsFromRefreshToken(refreshTokenDTO.refreshToken)
        val jwtToken = tokenService.refreshToken(refreshTokenDTO.refreshToken, userDetail)
        return AuthPort.AuthResponse(
            token = jwtToken.toTokenResponse()
        )
    }

    override fun logout(token: String, user: User) {
        if (jwtTokenUtils.isTokenExpired(token))
            throw com.lcaohoanq.ktservice.exceptions.ExpiredTokenException("Token is expired");

        tokenService.deleteToken(token, user);
    }
}