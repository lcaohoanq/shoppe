package org.langchain4j.apithrottling.domain.quota

interface IApiQuotaUtil {

    fun isRequestAllowed(userId : Long, apiEndpoint: String): Boolean
    fun isRequestAllowed(userId : Long, apiEndpoint: String, maxRequests: Int): Boolean
    fun isRequestAllowed(
        userId : Long,
        apiEndpoint: String,
        maxRequests: Int,
        resetTimeMillis: Long
    ): Boolean

}