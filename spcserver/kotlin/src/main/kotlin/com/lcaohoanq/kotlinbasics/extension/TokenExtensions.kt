package com.lcaohoanq.kotlinbasics.extension

import com.lcaohoanq.kotlinbasics.domain.token.Token
import com.lcaohoanq.kotlinbasics.domain.token.TokenPort

fun Token.toTokenResponse(): TokenPort.TokenResponse {
    return TokenPort.TokenResponse(
        id = this.id!!,
        token = this.token,
        refreshToken = this.refreshToken,
        refreshExpirationDate = this.refreshExpirationDate,
        expirationDate = this.expirationDate,
        tokenType = this.tokenType,
        isMobile = this.isMobile,
        revoked = this.revoked,
        expired = this.expired
    )
}