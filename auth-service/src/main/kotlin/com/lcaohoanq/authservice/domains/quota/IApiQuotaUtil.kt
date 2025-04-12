package com.lcaohoanq.authservice.domains.quota

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