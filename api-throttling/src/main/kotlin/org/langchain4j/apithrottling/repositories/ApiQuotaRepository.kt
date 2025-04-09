package org.langchain4j.apithrottling.repositories

import org.langchain4j.apithrottling.entities.ApiQuota
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ApiQuotaRepository : JpaRepository<ApiQuota, Long> {
    fun findByUserIdAndApiEndpoint(userId: Long, apiEndpoint: String): Optional<ApiQuota>
    fun findByUserId(userId: Long): List<ApiQuota>?
}