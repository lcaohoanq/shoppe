package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.loginhistory.LoginHistory
import com.lcaohoanq.common.dto.LoginHistoryPort

fun LoginHistory.toLoginHistoryResponse(): LoginHistoryPort.LoginHistoryResponse {
    return LoginHistoryPort.LoginHistoryResponse(
        id = this.id!!,
        loginAt = this.loginAt,
        ipAddress = this.ipAddress!!,
        userAgent = this.userAgent!!,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}
