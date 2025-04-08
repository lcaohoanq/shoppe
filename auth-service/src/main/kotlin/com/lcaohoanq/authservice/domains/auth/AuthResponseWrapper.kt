package com.lcaohoanq.authservice.domains.auth

data class AuthResponseWrapper(
    val message: String?,
    val data: AuthPort.AuthResponse?,
    val statusCode: Int?,
    val isSuccess: Boolean?,
    val reason: String? = null
)
