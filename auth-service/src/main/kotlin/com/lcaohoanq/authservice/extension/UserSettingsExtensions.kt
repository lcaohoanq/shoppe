package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.settings.UserSettings
import com.lcaohoanq.common.dto.UserPort

fun UserSettings.toUserSettingsResponse() = UserPort.UserSettingsResponse(
    id = this.id!!,
    userId = this.userId,
    twoFaEnabled = this.twoFaEnabled,
    preferredLanguage = this.preferredLanguage,
    darkMode = this.darkMode,
    emailNotificationsEnabled = this.emailNotificationsEnabled,
    loginAlerts = this.loginAlerts,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)