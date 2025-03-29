package com.lcaohoanq.kotlinbasics.domain.user

import com.lcaohoanq.kotlinbasics.api.PageResponse
import com.lcaohoanq.kotlinbasics.base.QueryCriteria
import com.lcaohoanq.kotlinbasics.utils.Sortable
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