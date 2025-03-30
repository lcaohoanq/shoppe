package com.lcaohoanq.kotlinbasics.repository

import com.lcaohoanq.kotlinbasics.domain.category.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
}