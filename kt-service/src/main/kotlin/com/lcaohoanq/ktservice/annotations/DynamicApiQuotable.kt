package com.lcaohoanq.ktservice.custom.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class DynamicApiQuotable(
    val endpoint: String,
    val memberMaxRequests: Int = 10, // Default for MEMBER
    val staffMaxRequests: Int = 20,  // Default for STAFF
    val adminMaxRequests: Int = 50   // Default for ADMIN
)
