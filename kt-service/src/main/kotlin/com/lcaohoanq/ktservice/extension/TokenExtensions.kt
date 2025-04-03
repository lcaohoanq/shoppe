package com.lcaohoanq.ktservice.extension

import com.lcaohoanq.common.dto.TokenPort
import com.lcaohoanq.ktservice.entities.Token

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