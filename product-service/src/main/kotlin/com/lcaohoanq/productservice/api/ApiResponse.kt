package com.lcaohoanq.productservice.api

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import io.swagger.v3.oas.annotations.media.Schema

@JsonPropertyOrder("message", "data", "status_code", "is_success", "reason")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "API Response")
data class ApiResponse<T>(
    @JsonProperty("status_code")
    var statusCode: Int? = null,

    @JsonProperty("message")
    @Schema(description = "Response message", example = "Login successfully")
    var message: String? = null,

    @JsonProperty("reason")
    @Schema(description = "Reason of response", example = "User not found")
    var reason: String? = null,

    @JsonProperty("is_success")
    @Schema(description = "Response status", example = "true")
    var isSuccess: Boolean? = null,

    @JsonProperty("data")
    @Schema(description = "Response data")
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

        fun build() = ApiResponse(
            statusCode = statusCode,
            message = message,
            reason = reason,
            isSuccess = isSuccess,
            data = data
        )
    }
}
