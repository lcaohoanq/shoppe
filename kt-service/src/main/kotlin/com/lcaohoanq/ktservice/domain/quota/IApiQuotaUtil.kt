package com.lcaohoanq.ktservice.domain.quota

interface IApiQuotaUtil {

    fun isRequestAllowed(userId : String, apiEndpoint: String): Boolean
    fun isRequestAllowed(userId : String, apiEndpoint: String, maxRequests: Int): Boolean
    fun isRequestAllowed(
        userId : String,
        apiEndpoint: String,
        maxRequests: Int,
        resetTimeMillis: Long
    ): Boolean

}