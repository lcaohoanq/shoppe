package com.lcaohoanq.ktservice.domain.quota

import com.lcaohoanq.ktservice.dto.QuotaPort
import com.lcaohoanq.ktservice.entities.ApiQuota

interface IApiQuotaService: IApiQuotaUtil {

    fun findQuotasByUserId(id: Long): List<ApiQuota>?
    fun updateQuota(userId: String, apiQuota: ApiQuota)
    fun findAllQuotas(): List<QuotaPort.QuotaResponse>

}