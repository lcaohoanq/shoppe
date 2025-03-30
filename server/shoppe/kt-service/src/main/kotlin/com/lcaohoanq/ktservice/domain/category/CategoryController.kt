package com.lcaohoanq.ktservice.domain.category

import com.lcaohoanq.ktservice.api.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Category", description = "Category API, for managing categories")
@RequestMapping("\${api.prefix}/categories")
class CategoryController(
    private val cateService: ICateService
) {

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    fun getAll(): ResponseEntity<ApiResponse<List<CatePort.CategoryResponse>>> {
        return ResponseEntity.ok(
            ApiResponse(
                message = "Get all categories successfully",
                data = cateService.getAll()
            )
        )
    }

}