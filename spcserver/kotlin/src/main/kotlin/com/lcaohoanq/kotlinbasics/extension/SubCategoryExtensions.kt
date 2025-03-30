package com.lcaohoanq.kotlinbasics.extension

import com.lcaohoanq.kotlinbasics.domain.category.SubCatePort
import com.lcaohoanq.kotlinbasics.domain.category.SubCategory

fun SubCategory.toResponse() = SubCatePort.SubCateResponse(
    id = this.id!!,
    name = this.name!!,
    createdAt = this.createdAt!!,
    updatedAt = this.updatedAt!!
)