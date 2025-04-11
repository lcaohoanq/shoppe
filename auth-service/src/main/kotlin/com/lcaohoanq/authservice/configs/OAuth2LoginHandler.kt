package com.lcaohoanq.authservice.configs

import com.lcaohoanq.authservice.components.JwtTokenUtils
import com.lcaohoanq.authservice.domains.auth.IAuthService
import com.lcaohoanq.authservice.domains.loginhistory.LoginHistory
import com.lcaohoanq.authservice.repositories.LoginHistoryRepository
import com.lcaohoanq.authservice.repositories.UserRepository
import com.lcaohoanq.common.dto.AuthPort
import com.lcaohoanq.common.enums.UserEnum
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.ObjectProvider
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component


@Component
class OAuth2LoginHandler(
    private val userRepository: UserRepository,
    private val authServiceProvider: ObjectProvider<IAuthService>,
    private val jwtTokenUtils: JwtTokenUtils,
    private val loginHistoryRepository: LoginHistoryRepository
): SavedRequestAwareAuthenticationSuccessHandler() {

    // Get authService only when needed
    private val authService: IAuthService
        get() = authServiceProvider.getObject()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {

        val token = authentication as OAuth2AuthenticationToken

        if ("google" == token.authorizedClientRegistrationId) {
            val principal = authentication.principal as DefaultOAuth2User
            val attributes = principal.attributes
            val email = attributes.getOrDefault("email", "").toString()
            val name = attributes.getOrDefault("name", "").toString()
            val imageUrl = attributes.getOrDefault("picture", "").toString()
            val user = userRepository.findByEmail(email) ?: run {
                authService.register(AuthPort.SignUpReq(
                    email = email,
                    address = "",
                    password = "",
                    phoneNumber = "",
                    name = name,
                    status = UserEnum.Status.VERIFIED
                ))

                userRepository.findByEmail(email) ?: throw IllegalStateException("User not found after register")
            }

            // ✅ Nếu user đã tồn tại, cập nhật lastLoginTimeStamp
            if (user.id != null) {
                user.lastLoginTimeStamp = java.time.LocalDateTime.now()
                userRepository.save(user)
            }

            val loginHistory = LoginHistory(
                user = user,
                ipAddress = request?.remoteAddr,
                userAgent = request?.getHeader("User-Agent"),
                success = true
            )
            loginHistoryRepository.save(loginHistory)

            val jwtToken: String = jwtTokenUtils.generateToken(user)
            val role: UserEnum.Role? = user.role
            val username: String = user.getUsername()
            val id: Long? = user.id
            val avatar: String = user.avatar ?: imageUrl

            val redirectUrl = "http://localhost:4000/auth/oauth2?token=$jwtToken&role=$role&username=$username&id=$id&avatar=$avatar"

            response?.sendRedirect(redirectUrl)
        }

        if ("github" == token.authorizedClientRegistrationId) {
            val principal = authentication.principal as DefaultOAuth2User
            val attributes = principal.attributes
            val email = attributes.getOrDefault("email", "").toString()
            val name = attributes.getOrDefault("name", "").toString()
            val imageUrl = attributes.getOrDefault("avatar_url", "").toString()
            val user = userRepository.findByEmail(email) ?: run {
                authService.register(AuthPort.SignUpReq(
                    email = email,
                    address = "",
                    password = "",
                    phoneNumber = "",
                    name = name,
                    status = UserEnum.Status.VERIFIED
                ))

                userRepository.findByEmail(email) ?: throw IllegalStateException("User not found after register")
            }

            // ✅ Nếu user đã tồn tại, cập nhật lastLoginTimeStamp
            if (user.id != null) {
                user.lastLoginTimeStamp = java.time.LocalDateTime.now()
                userRepository.save(user)
            }

            val loginHistory = LoginHistory(
                user = user,
                ipAddress = request?.remoteAddr,
                userAgent = request?.getHeader("User-Agent"),
                success = true
            )
            loginHistoryRepository.save(loginHistory)

            val jwtToken: String = jwtTokenUtils.generateToken(user)
            val role: UserEnum.Role? = user.role
            val username: String = user.getUsername()
            val id: Long? = user.id
            val avatar: String = user.avatar ?: imageUrl

            val redirectUrl = "http://localhost:4000/auth/oauth2?token=$jwtToken&role=$role&username=$username&id=$id&avatar=$avatar"

            response?.sendRedirect(redirectUrl)
        }

    }
}