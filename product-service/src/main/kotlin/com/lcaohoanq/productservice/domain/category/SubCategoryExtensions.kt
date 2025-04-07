package com.lcaohoanq.productservice.domain.category


fun SubCategory.toResponse() = SubCatePort.SubCateResponse(
    id = this.id!!,
    name = this.name!!,
    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!
)