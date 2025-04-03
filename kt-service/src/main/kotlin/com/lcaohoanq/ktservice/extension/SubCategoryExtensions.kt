package com.lcaohoanq.ktservice.extension


import com.lcaohoanq.common.dto.SubCatePort
import com.lcaohoanq.ktservice.entities.SubCategory

fun SubCategory.toResponse() = SubCatePort.SubCateResponse(
    id = this.id!!,
    name = this.name!!,
    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!
)