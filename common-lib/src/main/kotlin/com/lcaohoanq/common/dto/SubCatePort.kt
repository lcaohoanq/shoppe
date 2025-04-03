package com.lcaohoanq.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

class SubCatePort {

    @JsonPropertyOrder(
        "id", "name", "createdAt", "updatedAt"
    )
    data class SubCateResponse(
        val id: Long,
        val name: String,
        @JsonIgnore @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS"
        ) val createdAt: LocalDateTime,
        @JsonIgnore @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS"
        ) val updatedAt: LocalDateTime
    )
}