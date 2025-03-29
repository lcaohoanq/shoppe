package com.lcaohoanq.kotlinbasics.domain.quota

import com.lcaohoanq.kotlinbasics.domain.user.User

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