package com.lcaohoanq.ktservice.repository

import com.lcaohoanq.ktservice.domain.category.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
}