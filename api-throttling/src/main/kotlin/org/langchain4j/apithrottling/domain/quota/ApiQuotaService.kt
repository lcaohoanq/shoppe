package org.langchain4j.apithrottling.domain.quota

import com.lcaohoanq.common.metadata.QuotaMeta
import org.langchain4j.apithrottling.dto.QuotaPort
import org.langchain4j.apithrottling.entities.ApiQuota
import org.langchain4j.apithrottling.repositories.ApiQuotaRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class ApiQuotaService(private val apiQuotaRepository: ApiQuotaRepository) : IApiQuotaService {
    override fun findQuotasByUserId(id: Long): List<ApiQuota>? {
        return apiQuotaRepository.findByUserId(id)
    }

    override fun updateQuota(userId: Long, apiQuota: ApiQuota) {
        TODO("Not yet implemented")
    }

    override fun findAllQuotas(): List<QuotaPort.QuotaResponse> {
        return apiQuotaRepository.findAll().map {
            QuotaPort.QuotaResponse(
                userId = it.userId!!,
                quotas = listOf(
                    QuotaMeta(
                        apiEndpoint = it.apiEndpoint,
                        requestCount = it.requestCount,
                        maxRequests = it.maxRequests,
                        resetTime = it.resetTime
                    )
                )
            )
        }
    }

    /**
     * Check if the user is allowed to make a request to the API endpoint
     * This method uses the default maxRequests and resetTime values for the quota
     * So you can use this method if you want to have a fixed quota for all users
     * */
    @Deprecated("Use the overloaded method with maxRequests or with maxRequests and resetTimeMillis for enhanced flexibility")
    override fun isRequestAllowed(userId: Long, apiEndpoint: String): Boolean {
        val quota = apiQuotaRepository.findByUserIdAndApiEndpoint(userId, apiEndpoint).orElseGet {
            // If no quota exists, create a new one with initial values
            ApiQuota(
                userId = userId,
                apiEndpoint = apiEndpoint,
                requestCount = 0,
                maxRequests = 10, // You can configure this value per API
                resetTime = LocalDateTime.now().plusHours(1) // Example reset time
            )
        }

        if (LocalDateTime.now().isAfter(quota.resetTime)) {
            // Reset the quota
            quota.requestCount = 0
            quota.resetTime = LocalDateTime.now().plusHours(1) // Reset the time window
        }

        return if (quota.requestCount < quota.maxRequests) {
            // Allow the request and update the request count
            quota.requestCount++
            apiQuotaRepository.save(quota)
            true
        } else {
            false
        }
    }

    override fun isRequestAllowed(userId: Long, apiEndpoint: String, maxRequests: Int): Boolean {
        val quota = apiQuotaRepository.findByUserIdAndApiEndpoint(userId, apiEndpoint).orElseGet {
            ApiQuota(
                userId = userId,
                apiEndpoint = apiEndpoint,
                requestCount = 0,
                maxRequests = maxRequests,  // Set the role-based maxRequests
                resetTime = LocalDateTime.now().plusHours(1) // Reset time window
            )
        }

        if (LocalDateTime.now().isAfter(quota.resetTime)) {
            // Reset the quota if the time window has expired
            quota.requestCount = 0
            quota.resetTime = LocalDateTime.now().plusHours(1)
        }

        return if (quota.requestCount < quota.maxRequests) {
            quota.requestCount++
            apiQuotaRepository.save(quota)
            true
        } else {
            false
        }
    }

    override fun isRequestAllowed(
        userId: Long,
        apiEndpoint: String,
        maxRequests: Int,
        resetTimeMillis: Long
    ): Boolean {
        val quota = apiQuotaRepository.findByUserIdAndApiEndpoint(userId, apiEndpoint).orElseGet {
            ApiQuota(
                userId = userId,
                apiEndpoint = apiEndpoint,
                requestCount = 0,
                maxRequests = maxRequests,
                resetTime = LocalDateTime.now().plusSeconds(resetTimeMillis / 1000)
            )
        }

        // Convert current time and resetTime to milliseconds to compare
        val currentTimeMillis = LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()
        val resetTimeMillisFromDb = quota.resetTime.toInstant(ZoneOffset.UTC).toEpochMilli()

        if (currentTimeMillis >= resetTimeMillisFromDb) {
            // Reset the quota if the time window has expired
            quota.requestCount = 0
            quota.resetTime = LocalDateTime.now().plusSeconds(resetTimeMillis / 1000)
        }

        return if (quota.requestCount < quota.maxRequests) {
            quota.requestCount++
            apiQuotaRepository.save(quota)
            true
        } else {
            false
        }
    }
}