package com.lcaohoanq.productservice.domain.category

import com.lcaohoanq.productservice.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CateService(

    private val categoryRepository: CategoryRepository

): ICateService {
    override fun getAll(): List<CatePort.CategoryResponse> {
        return categoryRepository.findAll().map { it.toResponse() }
    }
}