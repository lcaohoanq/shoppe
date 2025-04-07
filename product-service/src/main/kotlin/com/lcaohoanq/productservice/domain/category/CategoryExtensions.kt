package com.lcaohoanq.productservice.domain.category

fun Category.toResponse() = CatePort.CategoryResponse(
    id = this.id!!,
    name = this.name,
    subcategories = this.subCategories.map { it.toResponse() }
        .toSet(), //internal mapping toResponse of SubCategory
    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!
)