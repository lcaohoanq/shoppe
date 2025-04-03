package com.lcaohoanq.ktservice.domain.user

import com.lcaohoanq.ktservice.api.PageResponse
import com.lcaohoanq.ktservice.bases.QueryCriteria
import com.lcaohoanq.ktservice.dto.UserPort
import com.lcaohoanq.ktservice.entities.User
import com.lcaohoanq.ktservice.extension.toUserResponse
import com.lcaohoanq.common.metadata.PaginationMeta
import com.lcaohoanq.ktservice.repositories.TokenRepository
import com.lcaohoanq.ktservice.repositories.UserRepository
import com.lcaohoanq.common.utils.SortCriterion
import com.lcaohoanq.common.utils.SortOrder
import com.lcaohoanq.common.utils.Sortable
import com.lcaohoanq.ktservice.component.JwtTokenUtils
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val jwtTokenUtils: JwtTokenUtils,
    private val tokenRepository: TokenRepository,
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

        return userRepository.findAll(
            searchSpecification,
            PageRequest.of(pageable.pageNumber, pageable.pageSize, sort)
        ).let { it ->
            PageResponse(
                message = "Get users successfully with query",
                data = it.content.map { it.toUserResponse() },
                paginationMeta = PaginationMeta(
                    totalPages = it.totalPages,
                    totalItems = it.totalElements,
                    currentPage = it.number,
                    pageSize = it.size,
                    search = queryCriteria.search,
                    sort = SortCriterion(
                        sortBy = queryCriteria.sortBy,
                        order = queryCriteria.sortOrder
                    )
                )
            )
        }
    }

    override fun getById(id: Long): UserPort.UserResponse? {
        return userRepository.findById(id).map {
            it.toUserResponse()
        }.orElse(null)
    }

    override fun create(user: UserPort.CreateUserDTO): User {

        if (userRepository.existsByEmail(user.email))
            throw DataIntegrityViolationException("Email already exists")

        return userRepository.save(
            User(
                email = user.email,
                hashedPassword = user.password,
                address = "",
                avatar = "https://api.dicebear.com/9.x/adventurer/svg?seed=${user.email}",
                userName = "",
                phone = ""
            )
        )
    }

    override fun isAccountExist(email: String, password: String): User? {
        return userRepository.findByEmailAndHashedPassword(email, password)
    }

    override fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email) ?: throw com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException(
            "Email not found"
        )
    }

    override fun getUserDetailsFromAccessToken(at: String): User {
        if (jwtTokenUtils.isTokenExpired(at)) throw com.lcaohoanq.ktservice.exceptions.ExpiredTokenException(
            "Token is expired"
        )
        val email = jwtTokenUtils.extractEmail(at)
        return userRepository.findByEmail(email) ?: throw com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException(
            "User not found"
        )
    }

    override fun getUserDetailsFromRefreshToken(rf: String): User {
        val existingToken = tokenRepository.findByRefreshToken(rf)
            ?: throw com.lcaohoanq.ktservice.exceptions.base.DataNotFoundException("Refresh Token not exist")
        return getUserDetailsFromAccessToken(existingToken.token)
    }
}