package com.lcaohoanq.ktservice.domain.category

import com.lcaohoanq.ktservice.extension.toResponse
import com.lcaohoanq.ktservice.repository.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CateService(

    private val categoryRepository: CategoryRepository

): ICateService {
    override fun getAll(): List<CatePort.CategoryResponse> {
        return categoryRepository.findAll().map { it.toResponse() }
    }
}