package com.lcaohoanq.kotlinbasics.domain.quota

import com.lcaohoanq.kotlinbasics.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.prefix}/quotas")
@Tag(name = "Quota", description = "Quota API, for managing API quotas")
class QuotaController(
    private val quotaService: IApiQuotaService
) {

    @Operation(summary = "Get all quotas", description = "Get all quotas")
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_STAFF')")
    fun findAll(): ResponseEntity<ApiResponse<List<QuotaPort.QuotaResponse>>> =
        ResponseEntity.ok(
            ApiResponse(
                message = "Get all quotas successfully",
                data = quotaService.findAllQuotas()
            )
        )

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_STAFF')")
    fun findByUser(@PathVariable id: Long): ResponseEntity<ApiResponse<List<ApiQuota>>> {
        val quotas = quotaService.findQuotasByUserId(id)
        return ResponseEntity.ok(
            ApiResponse(
                message = "Get quotas by user successfully",
                data = quotas
            )
        )
    }


}