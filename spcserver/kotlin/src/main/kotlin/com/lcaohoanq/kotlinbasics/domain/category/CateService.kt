package com.lcaohoanq.kotlinbasics.domain.category

import com.lcaohoanq.kotlinbasics.extension.toResponse
import com.lcaohoanq.kotlinbasics.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CateService(

    private val categoryRepository: CategoryRepository

): ICateService {
    override fun getAll(): List<CatePort.CategoryResponse> {
        return categoryRepository.findAll().map { it.toResponse() }
    }
}