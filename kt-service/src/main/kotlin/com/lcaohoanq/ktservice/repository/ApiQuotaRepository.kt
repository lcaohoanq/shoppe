package com.lcaohoanq.ktservice.repository

import com.lcaohoanq.ktservice.domain.quota.ApiQuota
import com.lcaohoanq.ktservice.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ApiQuotaRepository : JpaRepository<ApiQuota, Long> {
    fun findByUserAndApiEndpoint(user: User, apiEndpoint: String): Optional<ApiQuota>
    fun findByUserId(userId: Long): List<ApiQuota>?
}