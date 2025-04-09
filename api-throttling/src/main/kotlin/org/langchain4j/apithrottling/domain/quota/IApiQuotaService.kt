package org.langchain4j.apithrottling.domain.quota

import org.langchain4j.apithrottling.dto.QuotaPort
import org.langchain4j.apithrottling.entities.ApiQuota

interface IApiQuotaService: IApiQuotaUtil {

    fun findQuotasByUserId(id: Long): List<ApiQuota>?
    fun updateQuota(userId: Long, apiQuota: ApiQuota)
    fun findAllQuotas(): List<QuotaPort.QuotaResponse>

}