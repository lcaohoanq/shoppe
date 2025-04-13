package com.lcaohoanq.authservice.configs

import com.lcaohoanq.authservice.components.JwtTokenUtils
import com.lcaohoanq.authservice.domains.auth.IAuthService
import com.lcaohoanq.authservice.domains.loginhistory.ILoginHistoryService
import com.lcaohoanq.authservice.repositories.UserRepository
import com.lcaohoanq.authservice.utils.getClientIp
import com.lcaohoanq.authservice.utils.getUserAgent
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
    private val loginHistoryService: ILoginHistoryService
) : SavedRequestAwareAuthenticationSuccessHandler() {

    private val authService: IAuthService
        get() = authServiceProvider.getObject()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse?,
        authentication: Authentication?
    ) {
        val token = authentication as OAuth2AuthenticationToken
        val provider = token.authorizedClientRegistrationId
        val principal = authentication.principal as DefaultOAuth2User
        val attributes = principal.attributes

        val (email, name, avatarUrl) = when (provider) {
            "google" -> Triple(
                attributes["email"]?.toString() ?: "",
                attributes["name"]?.toString() ?: "Unknown",
                attributes["picture"]?.toString() ?: ""
            )
            "github" -> Triple(
                attributes["email"]?.toString() ?: "",
                attributes["name"]?.toString() ?: "Unknown",
                attributes["avatar_url"]?.toString() ?: ""
            )
            else -> throw IllegalArgumentException("Unsupported OAuth2 provider: $provider")
        }

        val user = userRepository.findByEmail(email) ?: run {
            authService.register(
                AuthPort.SignUpReq(
                    email = email,
                    address = "",
                    password = "",
                    phoneNumber = "",
                    name = name,
                    status = UserEnum.Status.VERIFIED
                )
            )
            userRepository.findByEmail(email) ?: throw IllegalStateException("User not found after registration")
        }

        // Record login
        loginHistoryService.recordLogin(user, request.getClientIp(), request.getUserAgent())

        // Generate token
        val jwtToken: String = jwtTokenUtils.generateToken(user)

        val redirectUrl = buildRedirectUrl(
            token = jwtToken,
            role = user.role,
            username = user.getUsername(),
            id = user.id,
            avatar = user.avatar.ifEmpty { avatarUrl }
        )

        response?.sendRedirect(redirectUrl)
    }

    private fun buildRedirectUrl(
        token: String,
        role: UserEnum.Role?,
        username: String,
        id: Long?,
        avatar: String
    ): String {
        return "http://localhost:4000/auth/oauth2" +
                "?token=$token" +
                "&role=${role?.name}" +
                "&username=$username" +
                "&id=$id" +
                "&avatar=$avatar"
    }
}
