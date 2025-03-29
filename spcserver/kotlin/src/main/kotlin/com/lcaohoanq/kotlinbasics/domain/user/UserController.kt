package com.lcaohoanq.kotlinbasics.domain.user

import com.lcaohoanq.kotlinbasics.api.ApiResponse
import com.lcaohoanq.kotlinbasics.api.PageResponse
import com.lcaohoanq.kotlinbasics.base.QueryCriteria
import com.lcaohoanq.kotlinbasics.domain.auth.IAuthService
import com.lcaohoanq.kotlinbasics.domain.quota.ApiQuotaService
import com.lcaohoanq.kotlinbasics.extension.toUserResponse
import com.lcaohoanq.kotlinbasics.utils.SortOrder
import com.lcaohoanq.kotlinbasics.utils.Sortable
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("\${api.prefix}/users")
@Tag(name = "User", description = "User API")
class UserController(
    private val userService: IUserService,
    private val authService: IAuthService,
    private val apiQuotaService: ApiQuotaService
) {

    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_STAFF')")
    fun getAllUsers(): ResponseEntity<ApiResponse<List<UserPort.UserResponse>>> {
        val endpoint = "/users/all"

        return if(apiQuotaService.isRequestAllowed(authService.getCurrentAuthenticatedUser(), endpoint)) {
            ResponseEntity.ok(
                ApiResponse(
                    message = "Get all users successfully",
                    data = userService.getAll()
                )
            )
        } else {
            ResponseEntity.status(429).body(
                ApiResponse(
                    message = "Too many requests",
                    data = null
                )
            )
        }
    }

    @GetMapping("")
    fun getUserPaged(
        @RequestParam(required = false, defaultValue = "0") page: Int,
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @RequestParam(required = false, defaultValue = "") search: String,
        @RequestParam(required = false, defaultValue = "ID") sortBy: Sortable.UserSortField,
        @RequestParam(required = false, defaultValue = "ASC") sortOrder: SortOrder,
    )
            : ResponseEntity<PageResponse<UserPort.UserResponse>> {

        val pageable = PageRequest.of(page, limit)
        val queryCriteria = QueryCriteria(search, sortBy, sortOrder)

        return ResponseEntity.ok(userService.getAll(pageable, queryCriteria))
    }

    @GetMapping("/details/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<ApiResponse<UserPort.UserResponse>> =
        ResponseEntity.ok(
            ApiResponse(
                message = "Get user by id successfully",
                data = userService.getById(id)
            )
        )

    /**
     * Get current authenticated user via Spring Security JWT
     * @return User
     */
    @Operation(summary = "Get user details from token", description = "Provide access token to get user details on the Header")
    @PatchMapping("/details")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_STAFF')")
    fun takeUserDetailsFromToken(): ResponseEntity<ApiResponse<UserPort.UserResponse>> {
        return ResponseEntity.ok(
            ApiResponse(
                message = "Get user details successfully",
                data = authService.getCurrentAuthenticatedUser().toUserResponse()
            )
        )
    }

}