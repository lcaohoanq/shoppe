package com.lcaohoanq.ktservice.domain.quota

import com.lcaohoanq.ktservice.entities.User

interface IApiQuotaUtil {

    fun isRequestAllowed(user: User, apiEndpoint: String): Boolean
    fun isRequestAllowed(user: User, apiEndpoint: String, maxRequests: Int): Boolean
    fun isRequestAllowed(
        user: User,
        apiEndpoint: String,
        maxRequests: Int,
        resetTimeMillis: Long
    ): Boolean

}