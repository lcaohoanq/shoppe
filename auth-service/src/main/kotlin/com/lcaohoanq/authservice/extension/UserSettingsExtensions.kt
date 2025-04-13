package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.settings.UserSettings
import com.lcaohoanq.common.dto.UserPort

fun UserSettings.toUserSettingsResponse() = UserPort.UserSettingsResponse(
    id = this.id!!,
    twoFaEnabled = this.twoFaEnabled,
    preferredLanguage = this.preferredLanguage,
    darkMode = this.darkMode,
    emailNotificationsEnabled = this.emailNotificationsEnabled,
    loginAlerts = this.loginAlerts,
    requestDisableAccount = this.requestDisableAccount,
    createdAt = this.createdAt,
    updatedAt = this.updatedAt
)