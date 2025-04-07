package com.lcaohoanq.productservice.repository

import com.lcaohoanq.productservice.domain.category.SubCategory
import org.springframework.data.jpa.repository.JpaRepository

interface SubCategoryRepository: JpaRepository<SubCategory, Long> {
}