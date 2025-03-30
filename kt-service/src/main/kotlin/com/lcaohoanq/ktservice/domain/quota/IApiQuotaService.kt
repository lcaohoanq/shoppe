package com.lcaohoanq.ktservice.domain.quota

import com.lcaohoanq.ktservice.domain.user.User

interface IApiQuotaService: IApiQuotaUtil {

    fun findQuotasByUserId(id: Long): List<ApiQuota>?
    fun updateQuota(user: User, apiQuota: ApiQuota)
    fun findAllQuotas(): List<QuotaPort.QuotaResponse>

}