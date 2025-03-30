package com.lcaohoanq.ktservice.domain.category

interface ICateService {

    fun getAll(): List<CatePort.CategoryResponse>

}