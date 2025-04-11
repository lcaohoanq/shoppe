package com.lcaohoanq.authservice.domains.token

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.common.dto.TokenPort

interface ITokenService {

    fun getAll(): List<TokenPort.TokenResponse>
    fun addToken(userId: Long, token: String): Token
    fun refreshToken(refreshToken: String, user: User): Token
    fun deleteToken(token: String, user: User)
    fun findUserByToken(token: String): Token

}