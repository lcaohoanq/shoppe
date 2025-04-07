package com.lcaohoanq.authservice.domains.user

import com.lcaohoanq.authservice.dto.UserPort
import com.lcaohoanq.common.apis.PageResponse
import com.lcaohoanq.common.metadata.QueryCriteria
import com.lcaohoanq.common.utils.Sortable
import org.springframework.data.domain.Pageable

interface IUserService {

    fun getAll(): List<UserPort.UserResponse>;
    fun getAll(pageable: Pageable, queryCriteria: QueryCriteria<Sortable.UserSortField>): PageResponse<UserPort.UserResponse>;
    fun getById(id: Long): UserPort.UserResponse?;
    fun create(user: UserPort.CreateUserDTO) : User;
    fun isAccountExist(email: String, password: String): User?
    fun findByEmail(email: String): User?
    fun getUserDetailsFromAccessToken(at: String): User
    fun getUserDetailsFromRefreshToken(rf: String): User
}