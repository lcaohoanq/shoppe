package com.lcaohoanq.authservice.domains.quota

import com.lcaohoanq.common.dto.QuotaPort

interface IApiQuotaService: IApiQuotaUtil {

    fun findQuotasByUserId(id: Long): List<ApiQuota>?
    fun updateQuota(userId: Long, apiQuota: ApiQuota)
    fun findAllQuotas(): List<QuotaPort.QuotaResponse>

}