package com.lcaohoanq.kotlinbasics.domain.category

interface ICateService {

    fun getAll(): List<CatePort.CategoryResponse>

}