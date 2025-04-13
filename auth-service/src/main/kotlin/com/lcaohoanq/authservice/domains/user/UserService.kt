package com.lcaohoanq.authservice.domains.user

import com.lcaohoanq.authservice.components.JwtTokenUtils
import com.lcaohoanq.authservice.extension.UserResponseOptions
import com.lcaohoanq.authservice.extension.toLoginHistoryResponse
import com.lcaohoanq.common.dto.UserPort
import com.lcaohoanq.authservice.extension.toUserResponse
import com.lcaohoanq.authservice.extension.toUserSettingsResponse
import com.lcaohoanq.authservice.repositories.LoginHistoryRepository
import com.lcaohoanq.authservice.repositories.TokenRepository
import com.lcaohoanq.authservice.repositories.UserRepository
import com.lcaohoanq.authservice.repositories.UserSettingsRepository
import com.lcaohoanq.common.apis.PageResponse
import com.lcaohoanq.common.metadata.PaginationMeta
import com.lcaohoanq.common.metadata.QueryCriteria
import com.lcaohoanq.common.utils.SortCriterion
import com.lcaohoanq.common.utils.SortOrder
import com.lcaohoanq.common.utils.Sortable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenUtils: JwtTokenUtils,
    private val tokenRepository: TokenRepository,
    private val loginHistoryRepository: LoginHistoryRepository,
    private val userSettingsRepository: UserSettingsRepository,
) : IUserService {
    override fun getAll(): List<UserPort.UserResponse> {
        return userRepository.findAll().map { it.toUserResponse() }
    }

    override fun getAll(
        pageable: Pageable,
        queryCriteria: QueryCriteria<Sortable.UserSortField>
    ): PageResponse<UserPort.UserResponse> {
        val searchSpecification = UserSpecification(queryCriteria.search)

        val sortField = queryCriteria.sortBy.field
        val sortOrder =
            if (queryCriteria.sortOrder == SortOrder.ASC) Sort.Direction.ASC else Sort.Direction.DESC

        val sort = Sort.by(sortOrder, sortField)

        val pageResult = userRepository.findAll(
            searchSpecification,
            PageRequest.of(pageable.pageNumber, pageable.pageSize, sort)
        )

        // Fetch login history chỉ khi bạn cần (ví dụ ở đây đang bật mặc định luôn, có thể tùy biến sau)
        val userResponses = pageResult.content.map { user ->
            val top5LoginHistory = loginHistoryRepository
                .findTop5ByUserIdOrderByLoginAtDesc(user.id!!)
                .map { it.toLoginHistoryResponse() }
                .toMutableList()

            // Get user settings for the current user
            val userSettings = userSettingsRepository.findByUserId(userId = user.id!!)
                .toUserSettingsResponse()

            val options = UserResponseOptions(
                includeLoginHistory = true,
                loginHistory = top5LoginHistory,
                includeSettings = true,
                settings = userSettings
            )

            user.toUserResponse(options)
        }

        return PageResponse(
            message = "Get users successfully with query",
            data = userResponses,
            paginationMeta = PaginationMeta(
                totalPages = pageResult.totalPages,
                totalItems = pageResult.totalElements,
                currentPage = pageResult.number,
                pageSize = pageResult.size,
                search = queryCriteria.search,
                sort = SortCriterion(
                    sortBy = queryCriteria.sortBy,
                    order = queryCriteria.sortOrder
                )
            )
        )
    }

    override fun getById(id: Long): UserPort.UserResponse? {
        return userRepository.findById(id).map {
            user -> user.toUserResponse(
                UserResponseOptions(
                    includeLoginHistory = true,
                    loginHistory = loginHistoryRepository
                        .findTop5ByUserIdOrderByLoginAtDesc(user.id!!)
                        .map { it.toLoginHistoryResponse() }
                        .toMutableList(),
                    includeSettings = true,
                    settings = user.userSettings?.toUserSettingsResponse()
                )
            )
        }.orElse(null)
    }

    override fun isAccountExist(email: String, password: String): User? {
        return userRepository.findByEmailAndHashedPassword(email, password)
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email) ?: throw com.lcaohoanq.common.exceptions.base.DataNotFoundException(
            "Email not found"
        )
    }

    override fun getUserDetailsFromAccessToken(at: String): User {
        if (jwtTokenUtils.isTokenExpired(at)) throw com.lcaohoanq.common.exceptions.ExpiredTokenException(
            "Token is expired"
        )
        val email = jwtTokenUtils.extractEmail(at)
        return userRepository.findByEmail(email) ?: throw com.lcaohoanq.common.exceptions.base.DataNotFoundException(
            "User not found"
        )
    }

    override fun getUserDetailsFromRefreshToken(rf: String): User {
        val existingToken = tokenRepository.findByRefreshToken(rf)
            ?: throw com.lcaohoanq.common.exceptions.base.DataNotFoundException("Refresh Token not exist")
        return getUserDetailsFromAccessToken(existingToken.token)
    }
}