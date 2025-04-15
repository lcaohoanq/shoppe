package com.lcaohoanq.authservice.domains.auth

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.common.dto.TokenPort
import com.lcaohoanq.authservice.domains.user.UserPort
import com.lcaohoanq.common.dto.AuthPort


interface IAuthService {

    fun login(account: AuthPort.AuthRequest): LoginResult
    fun register(newAccount: AuthPort.SignUpReq)
    fun getUserDetailsFromToken(token: String): UserPort.UserResponse
    fun getCurrentAuthenticatedUser(): User
    fun refreshToken(refreshTokenDTO: TokenPort.RefreshTokenDTO): AuthPort.AuthResponse
    fun logout(token: String, user: User)
    fun generateTokenFromEmail(email: String): String
    fun changePassword(req: AuthPort.ChangePasswordReq)
    fun verifyAccount(token: String): Unit
    fun disableAccount(token: String)
    fun setup2FA(email: String): String
    fun verify2fa(data: AuthPort.Verify2FAReq)

}