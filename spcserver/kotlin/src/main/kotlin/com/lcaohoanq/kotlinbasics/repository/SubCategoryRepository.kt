package com.lcaohoanq.kotlinbasics.repository

import com.lcaohoanq.kotlinbasics.domain.category.SubCategory
import org.springframework.data.jpa.repository.JpaRepository

interface SubCategoryRepository: JpaRepository<SubCategory, Long> {
}