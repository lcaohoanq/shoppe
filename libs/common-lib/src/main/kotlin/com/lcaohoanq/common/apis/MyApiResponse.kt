package com.lcaohoanq.common.apis

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonPropertyOrder("message", "data", "statusCode", "isSuccess", "reason")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class MyApiResponse<T>(
    var statusCode: Int? = null,

    var message: String? = null,

    var reason: String? = null,

    var isSuccess: Boolean? = null,

    var data: T? = null
) {

    companion object {
        fun <T> builder() = Builder<T>()
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

        fun build() = MyApiResponse(
            statusCode = statusCode,
            message = message,
            reason = reason,
            isSuccess = isSuccess,
            data = data
        )
    }
}
