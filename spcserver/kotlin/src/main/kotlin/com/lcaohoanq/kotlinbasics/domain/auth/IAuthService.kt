package com.lcaohoanq.kotlinbasics.domain.auth

import com.lcaohoanq.kotlinbasics.domain.token.TokenPort
import com.lcaohoanq.kotlinbasics.domain.user.User
import com.lcaohoanq.kotlinbasics.domain.user.UserPort

interface IAuthService {

    fun login(account: AuthPort.AuthRequest): AuthPort.AuthResponse
    fun register(newAccount: AuthPort.SignUpReq)
    fun getUserDetailsFromToken(token: String): UserPort.UserResponse
    fun getCurrentAuthenticatedUser(): User
    fun refreshToken(refreshTokenDTO: TokenPort.RefreshTokenDTO): AuthPort.AuthResponse
    fun logout(token: String, user: User)
}