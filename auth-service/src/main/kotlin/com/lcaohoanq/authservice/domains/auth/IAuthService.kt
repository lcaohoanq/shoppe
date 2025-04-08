package com.lcaohoanq.authservice.domains.auth

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.common.dto.TokenPort
import com.lcaohoanq.authservice.dto.UserPort
import com.lcaohoanq.common.dto.AuthPort


interface IAuthService {

    fun login(account: AuthPort.AuthRequest): AuthPort.AuthResponse
    fun register(newAccount: AuthPort.SignUpReq)
    fun getUserDetailsFromToken(token: String): UserPort.UserResponse
    fun getCurrentAuthenticatedUser(): User
    fun refreshToken(refreshTokenDTO: TokenPort.RefreshTokenDTO): AuthPort.AuthResponse
    fun logout(token: String, user: User)
    fun generateTokenFromEmail(email: String): String
    fun verifyAccount(token: String): Unit

}