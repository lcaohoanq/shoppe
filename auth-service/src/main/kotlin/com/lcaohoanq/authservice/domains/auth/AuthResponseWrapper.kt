package com.lcaohoanq.authservice.domains.auth

import com.fasterxml.jackson.annotation.JsonProperty

data class AuthResponseWrapper(
    val message: String?,
    val data: AuthPort.AuthResponse?,
    val statusCode: Int?,
    val isSuccess: Boolean?,
    val reason: String? = null
)
