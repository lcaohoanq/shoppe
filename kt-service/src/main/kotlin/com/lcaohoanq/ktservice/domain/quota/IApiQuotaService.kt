package com.lcaohoanq.ktservice.domain.quota

import com.lcaohoanq.common.dto.QuotaPort
import com.lcaohoanq.ktservice.entities.ApiQuota
import com.lcaohoanq.ktservice.entities.User

interface IApiQuotaService: IApiQuotaUtil {

    fun findQuotasByUserId(id: Long): List<ApiQuota>?
    fun updateQuota(user: User, apiQuota: ApiQuota)
    fun findAllQuotas(): List<QuotaPort.QuotaResponse>

}