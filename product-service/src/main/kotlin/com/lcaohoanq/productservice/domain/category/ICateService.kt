package com.lcaohoanq.productservice.domain.category

interface ICateService {

    fun getAll(): List<CatePort.CategoryResponse>

}