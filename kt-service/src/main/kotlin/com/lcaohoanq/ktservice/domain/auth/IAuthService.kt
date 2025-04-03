package com.lcaohoanq.ktservice.domain.auth

import com.lcaohoanq.ktservice.dto.TokenPort
import com.lcaohoanq.ktservice.entities.User
import com.lcaohoanq.ktservice.dto.UserPort

interface IAuthService {

    fun login(account: AuthPort.AuthRequest): AuthPort.AuthResponse
    fun register(newAccount: AuthPort.SignUpReq)
    fun getUserDetailsFromToken(token: String): UserPort.UserResponse
    fun getCurrentAuthenticatedUser(): User
    fun refreshToken(refreshTokenDTO: TokenPort.RefreshTokenDTO): AuthPort.AuthResponse
    fun logout(token: String, user: User)
}