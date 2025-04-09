package com.lcaohoanq.productservice.extensions

import com.lcaohoanq.productservice.domain.category.CatePort
import com.lcaohoanq.productservice.domain.category.Category
import com.lcaohoanq.productservice.domain.category.toResponse

fun Category.toResponse() = CatePort.CategoryResponse(
    id = this.id!!,
    name = this.name,
    subcategories = this.subCategories.map { it.toResponse() }
        .toSet(), //internal mapping toResponse of SubCategory
    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!
)