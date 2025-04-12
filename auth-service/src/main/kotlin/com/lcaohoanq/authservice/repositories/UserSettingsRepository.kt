package com.lcaohoanq.authservice.repositories

import com.lcaohoanq.authservice.domains.settings.UserSettings
import org.springframework.data.jpa.repository.JpaRepository

interface UserSettingsRepository: JpaRepository<UserSettings, Long> {

    fun findByUserId(userId: Long): UserSettings

}