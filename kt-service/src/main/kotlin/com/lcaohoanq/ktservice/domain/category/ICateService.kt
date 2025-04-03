package com.lcaohoanq.ktservice.domain.category

import com.lcaohoanq.common.dto.CatePort

interface ICateService {

    fun getAll(): List<CatePort.CategoryResponse>

}