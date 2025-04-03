package com.lcaohoanq.ktservice.domain.category

import com.lcaohoanq.ktservice.dto.CatePort

interface ICateService {

    fun getAll(): List<CatePort.CategoryResponse>

}