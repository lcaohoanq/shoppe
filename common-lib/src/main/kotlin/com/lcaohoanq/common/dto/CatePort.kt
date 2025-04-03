package com.lcaohoanq.common.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import java.time.LocalDateTime

class CatePort {

    @JsonPropertyOrder(
        "id", "name", "subCate", "createdAt", "updatedAt"
    )
    data class CategoryResponse(
        val id: Long,
        val name: String,

        @JsonProperty("subCate") val subcategories: Set<SubCatePort.SubCateResponse>,

        @JsonIgnore @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS"
        ) val createdAt: LocalDateTime,

        @JsonIgnore @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS"
        ) val updatedAt: LocalDateTime
    ) {

    }

}