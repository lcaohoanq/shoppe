package com.lcaohoanq.kotlinbasics.domain.token

import com.lcaohoanq.kotlinbasics.domain.user.User


interface ITokenService {

    fun addToken(userId: Long, token: String): Token
    fun refreshToken(refreshToken: String, user: User): Token
    fun deleteToken(token: String, user: User)
    fun findUserByToken(token: String): Token

}