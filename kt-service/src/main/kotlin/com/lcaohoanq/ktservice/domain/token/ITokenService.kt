package com.lcaohoanq.ktservice.domain.token

import com.lcaohoanq.ktservice.domain.user.User


interface ITokenService {

    fun addToken(userId: Long, token: String): Token
    fun refreshToken(refreshToken: String, user: User): Token
    fun deleteToken(token: String, user: User)
    fun findUserByToken(token: String): Token

}