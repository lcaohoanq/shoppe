package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.token.Token
import com.lcaohoanq.authservice.dto.TokenPort


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