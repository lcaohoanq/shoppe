package com.lcaohoanq.ktservice.domain.quota

interface QuotaPort {

    data class QuotaResponse(
        val userId: Long,
        val quotas: List<QuotaMeta>
    )

}