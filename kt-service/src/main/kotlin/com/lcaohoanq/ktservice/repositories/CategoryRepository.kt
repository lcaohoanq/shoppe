package com.lcaohoanq.ktservice.repositories

import com.lcaohoanq.ktservice.entities.Category
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository: JpaRepository<Category, Long> {
}