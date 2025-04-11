package com.lcaohoanq.authservice.domains.auth

import com.lcaohoanq.authservice.clients.MailFeignClient
import com.lcaohoanq.authservice.components.JwtTokenUtils
import com.lcaohoanq.authservice.domains.token.Token
import com.lcaohoanq.authservice.domains.token.TokenService
import com.lcaohoanq.authservice.domains.user.Address
import com.lcaohoanq.authservice.domains.user.IUserService
import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.common.dto.TokenPort
import com.lcaohoanq.common.dto.UserPort.UserResponse
import com.lcaohoanq.authservice.extension.toTokenResponse
import com.lcaohoanq.authservice.extension.toUserResponse
import com.lcaohoanq.authservice.repositories.AddressRepository
import com.lcaohoanq.authservice.repositories.UserRepository
import com.lcaohoanq.common.dto.AuthPort
import com.lcaohoanq.common.enums.UserEnum
import com.lcaohoanq.common.exceptions.ExpiredTokenException
import com.lcaohoanq.common.exceptions.MalformBehaviourException
import com.lcaohoanq.common.exceptions.base.DataNotFoundException
import mu.KotlinLogging
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class AuthService(
    private val userService: IUserService,
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val jwtTokenUtils: JwtTokenUtils,
    private val tokenService: TokenService,
    private val passwordEncoder: PasswordEncoder,
    private val mailFeignClient: MailFeignClient,
    private val addressRepository: AddressRepository
) : IAuthService {

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


        if (existUser.lastLoginTimeStamp == null) {
            try {
                mailFeignClient.sendGreetingUserLoginEmail(existUser.email)
            } catch (e: Exception) {
                log.error("Error when sending greeting email: ${e.message}")
            }
        }

        existUser.lastLoginTimeStamp = LocalDateTime.now()
        userRepository.save(existUser)

        // Return the authentication response
        return AuthPort.AuthResponse(
            token = jwtToken.toTokenResponse()
        )
    }


    override fun register(newAccount: AuthPort.SignUpReq) {

        if (userRepository.existsByEmail(newAccount.email)) {
            throw DataIntegrityViolationException("Email is already taken")
        }

//        mailFeignClient.sendOtp(newAccount.email)

        val newUser = User(
            email = newAccount.email,
            hashedPassword = passwordEncoder.encode(newAccount.password),
            role = UserEnum.Role.MEMBER,
            name = newAccount.name,
            status = UserEnum.Status.UNVERIFIED,
            userName = "",
            phone = "",
            avatar = "https://api.dicebear.com/9.x/adventurer/svg?seed=${newAccount.email}",
            cartId = "cart-${newAccount.email}",
            walletId = "wallet-${newAccount.email}",
        )

        userRepository.save(newUser)

        addressRepository.save(
            Address(
                address = newAccount.address,
                isDefault = true,
                phoneNumber = newAccount.phoneNumber,
                nameOfUser = newAccount.name,
                userId = newUser.id!!
            )
        )

        try {
            mailFeignClient.sendVerifyAccountEmail(
                AuthPort.VerifyEmailReq(newAccount.email)
            )
        } catch (e: Exception) {
            log.error("Error when sending email: ${e.message}")
        }

        log.info("New user registered successfully");
    }

    override fun getUserDetailsFromToken(token: String): UserResponse {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw ExpiredTokenException("Token is expired")
        }
        val email = jwtTokenUtils.extractEmail(token)
        val user = userRepository.findByEmail(email)
        if (user != null) {
            return user.toUserResponse()
        } else {
            throw DataNotFoundException("User not found")
        }

    }

    override fun getCurrentAuthenticatedUser(): User {
        return userService.findByEmail(
            SecurityContextHolder.getContext().authentication.name
        ) ?: throw DataNotFoundException("User not found")
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
            throw ExpiredTokenException("Token is expired");

        tokenService.deleteToken(token, user);
    }

    override fun generateTokenFromEmail(email: String): String {

        val user = userRepository.findByEmail(email)
            ?: throw DataNotFoundException("User not found")

        return jwtTokenUtils.generateToken(user)
            .also { token ->
                tokenService.addToken(user.id!!, token)
            }

    }

    override fun verifyAccount(token: String) {
        if (jwtTokenUtils.isTokenExpired(token)) {
            throw ExpiredTokenException("Token is expired")
        }

        val email = jwtTokenUtils.extractEmail(token)
        val user = userRepository.findByEmail(email)
            ?: throw DataNotFoundException("User not found")

        if (user.status == UserEnum.Status.VERIFIED)
            throw MalformBehaviourException("User already verified")

        user.status = UserEnum.Status.VERIFIED
        userRepository.save(user)

        tokenService.deleteToken(token, user)
    }
}