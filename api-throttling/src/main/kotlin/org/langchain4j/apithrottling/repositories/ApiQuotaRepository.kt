package org.langchain4j.apithrottling.repositories

import com.lcaohoanq.ktservice.entities.ApiQuota
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ApiQuotaRepository : JpaRepository<ApiQuota, Long> {
    fun findByUserAndApiEndpoint(userId: String, apiEndpoint: String): Optional<ApiQuota>
    fun findByUserId(userId: Long): List<ApiQuota>?
}