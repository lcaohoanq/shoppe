package com.lcaohoanq.ktservice.dto

import com.lcaohoanq.common.metadata.QuotaMeta

interface QuotaPort {

    data class QuotaResponse(
        val userId: Long,
        val quotas: List<QuotaMeta>
    )

}