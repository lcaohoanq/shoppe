package com.lcaohoanq.ktservice.dto

import com.lcaohoanq.common.metadata.QuotaMeta

interface QuotaPort {

    data class QuotaResponse(
        val userId: String,
        val quotas: List<QuotaMeta>
    )

}