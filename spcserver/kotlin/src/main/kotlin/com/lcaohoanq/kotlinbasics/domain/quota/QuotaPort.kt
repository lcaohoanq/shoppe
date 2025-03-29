package com.lcaohoanq.kotlinbasics.domain.quota

import com.lcaohoanq.kotlinbasics.domain.user.UserPort

interface QuotaPort {

    data class QuotaResponse(
        val userId: Long,
        val quotas: List<QuotaMeta>
    )

}