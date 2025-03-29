package com.lcaohoanq.kotlinbasics.domain.quota

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class QuotaMeta(
    val apiEndpoint: String,
    val requestCount: Int,
    val maxRequests: Int,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Ho_Chi_Minh") val resetTime: LocalDateTime
)
