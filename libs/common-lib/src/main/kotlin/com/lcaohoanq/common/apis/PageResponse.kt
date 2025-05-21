package com.lcaohoanq.common.apis

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import com.lcaohoanq.common.metadata.PaginationMeta

@JsonPropertyOrder("message", "data", "pagination_meta", "status_code", "is_success", "reason")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class PageResponse<T>(
    @JsonProperty("status_code")
    var statusCode: Int? = null,

    @JsonProperty("message")
    var message: String? = null,

    @JsonProperty("reason")
    var reason: String? = null,

    @JsonProperty("is_success")
    var isSuccess: Boolean? = null,

    @JsonProperty("data")
    var data: Collection<T>? = null,

    @JsonProperty("pagination_meta")
    var paginationMeta: PaginationMeta? = null
) {
    companion object {
        fun <T> builder() = Builder<T>()
    }

    class Builder<T> {
        private var statusCode: Int? = null
        private var message: String? = null
        private var reason: String? = null
        private var isSuccess: Boolean? = null
        private var data: Collection<T>? = null
        private var paginationMeta: PaginationMeta? = null

        fun statusCode(statusCode: Int?) = apply { this.statusCode = statusCode }
        fun message(message: String?) = apply { this.message = message }
        fun reason(reason: String?) = apply { this.reason = reason }
        fun isSuccess(isSuccess: Boolean?) = apply { this.isSuccess = isSuccess }
        fun data(data: Collection<T>?) = apply { this.data = data }
        fun paginationMeta(paginationMeta: PaginationMeta?) = apply { this.paginationMeta = paginationMeta }

        fun build() = PageResponse(
            statusCode = statusCode,
            message = message,
            reason = reason,
            isSuccess = isSuccess,
            data = data,
            paginationMeta = paginationMeta
        )
    }
}
