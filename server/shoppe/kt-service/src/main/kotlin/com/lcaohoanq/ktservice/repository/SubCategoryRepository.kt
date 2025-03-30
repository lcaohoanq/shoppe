package com.lcaohoanq.ktservice.repository

import com.lcaohoanq.ktservice.domain.category.SubCategory
import org.springframework.data.jpa.repository.JpaRepository

interface SubCategoryRepository: JpaRepository<SubCategory, Long> {
}