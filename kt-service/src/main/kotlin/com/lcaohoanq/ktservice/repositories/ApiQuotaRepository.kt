package com.lcaohoanq.ktservice.repositories

import com.lcaohoanq.ktservice.entities.ApiQuota
import com.lcaohoanq.ktservice.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ApiQuotaRepository : JpaRepository<ApiQuota, Long> {
    fun findByUserAndApiEndpoint(user: User, apiEndpoint: String): Optional<ApiQuota>
    fun findByUserId(userId: Long): List<ApiQuota>?
}