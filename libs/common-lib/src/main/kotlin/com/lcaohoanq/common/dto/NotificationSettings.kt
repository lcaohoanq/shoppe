package com.lcaohoanq.common.dto

data class NotificationSettingsDto(
    val email: EmailNotificationSettingsDto,
    val sms: SmsNotificationSettingsDto,
    val zalo: ZaloNotificationSettingsDto,
)

data class EmailNotificationSettingsDto(
    val masterEnabled: Boolean,
    val order: Boolean,
    val promo: Boolean,
    val survey: Boolean,
)

data class SmsNotificationSettingsDto(
    val masterEnabled: Boolean,
    val promo: Boolean,
)

data class ZaloNotificationSettingsDto(
    val masterEnabled: Boolean,
    val promo: Boolean,
)

