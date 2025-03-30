package com.lcaohoanq.ktservice.extension

import com.lcaohoanq.ktservice.domain.category.SubCatePort
import com.lcaohoanq.ktservice.domain.category.SubCategory

fun SubCategory.toResponse() = SubCatePort.SubCateResponse(
    id = this.id!!,
    name = this.name!!,
    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!
)