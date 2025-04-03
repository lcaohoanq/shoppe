package com.lcaohoanq.ktservice.extension

import com.lcaohoanq.common.dto.CatePort
import com.lcaohoanq.ktservice.entities.Category

fun Category.toResponse() = CatePort.CategoryResponse(
    id = this.id!!,
    name = this.name,
    subcategories = this.subCategories.map { it.toResponse() }.toSet(), //internal mapping toResponse of SubCategory
    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!
)