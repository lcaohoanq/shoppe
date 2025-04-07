package com.lcaohoanq.productservice.api

import com.fasterxml.jackson.annotation.JsonPropertyOrder


@JsonPropertyOrder("message", "reason", "status_code", "is_success", "data")
data class ApiError<T>(
    val statusCode: Int? = null,
    val message: String? = null,
    val reason: String? = null,
    val isSuccess: Boolean? = null,
    val data: T? = null
){

    companion object {
        fun <T> errorBuilder() = Builder<T>()
    }

    class Builder<T> {
        private var statusCode: Int? = null
        private var message: String? = null
        private var reason: String? = null
        private var isSuccess: Boolean? = null
        private var data: T? = null

        fun statusCode(statusCode: Int?) = apply { this.statusCode = statusCode }
        fun message(message: String?) = apply { this.message = message }
        fun reason(reason: String?) = apply { this.reason = reason }
        fun isSuccess(isSuccess: Boolean?) = apply { this.isSuccess = isSuccess }
        fun data(data: T?) = apply { this.data = data }

        fun build(): ApiError<T> = ApiError(
            statusCode = statusCode,
            message = message,
            reason = reason,
            isSuccess = isSuccess,
            data = data
        )
    }
}

