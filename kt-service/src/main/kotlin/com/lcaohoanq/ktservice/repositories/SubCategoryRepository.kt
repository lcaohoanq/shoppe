package com.lcaohoanq.ktservice.repositories

import com.lcaohoanq.ktservice.entities.SubCategory
import org.springframework.data.jpa.repository.JpaRepository

interface SubCategoryRepository: JpaRepository<SubCategory, Long> {
}