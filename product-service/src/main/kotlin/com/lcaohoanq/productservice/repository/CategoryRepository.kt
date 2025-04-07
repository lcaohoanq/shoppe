package com.lcaohoanq.productservice.repository

import com.lcaohoanq.productservice.domain.category.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
}