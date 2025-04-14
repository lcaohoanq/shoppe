package com.lcaohoanq.authservice.extension

import com.lcaohoanq.authservice.domains.user.User
import com.lcaohoanq.common.dto.LoginHistoryPort
import com.lcaohoanq.authservice.domains.user.UserPort

data class UserResponseOptions(
    val includeLoginHistory: Boolean = false,
    val loginHistory: MutableList<LoginHistoryPort.LoginHistoryResponse>? = null,
    val includeSettings: Boolean = false,
    val settings: UserPort.UserSettingsResponse? = null,
)

fun User.toUserResponse(
    opts: UserResponseOptions = UserResponseOptions()
): UserPort.UserResponse {
    return UserPort.UserResponse(
        id = id!!,
        email = email,
        password = hashedPassword,
        username = userName,
        phone = phone,
        status = status!!,
        totp = totpSecret,
        loginHistory = if (opts.includeLoginHistory) {
            opts.loginHistory ?: this.loginHistory
                .sortedByDescending { it.loginAt }
                .take(5)
                .map { it.toLoginHistoryResponse() }
                .toMutableList()
        } else mutableListOf(),

        settings = if (opts.includeSettings) {
            opts.settings ?: this.userSettings?.toUserSettingsResponse()
        } else UserPort.UserSettingsResponse(),

        role = role!!,
        avatar = avatar,
        address = address.map { it.toAddressResponse() }.toMutableList(),
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

