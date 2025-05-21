package com.lcaohoanq.gateway.qr

import java.time.LocalDateTime

data class QrLoginSession(
    val id: String,
    var status: QrStatus = QrStatus.PENDING,
    var userId: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val expiresAt: LocalDateTime = LocalDateTime.now().plusMinutes(5)
) {
    fun isExpired(): Boolean = LocalDateTime.now().isAfter(expiresAt)
}

enum class QrStatus {
    PENDING, APPROVED, EXPIRED
}
