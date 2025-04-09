package org.langchain4j.apithrottling.aspects

import com.lcaohoanq.ktservice.configs.roleQuotaConfig
import com.lcaohoanq.ktservice.custom.annotations.ApiQuotable
import com.lcaohoanq.ktservice.custom.annotations.DynamicApiQuotable
import com.lcaohoanq.ktservice.domain.auth.AuthService
import com.lcaohoanq.ktservice.domain.quota.ApiQuotaService
import com.lcaohoanq.ktservice.entities.User
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class ApiQuotaAspect(
    private val apiQuotaService: ApiQuotaService,
    private val authService: AuthService
) {

    // hard code quota
    @Pointcut("@annotation(apiQuotable)")
    fun apiQuotableAnnotation(apiQuotable: ApiQuotable) {
    }

    @Before("apiQuotableAnnotation(apiQuotable)")
    fun checkApiQuota(joinPoint: JoinPoint, apiQuotable: ApiQuotable) {
        val user = authService.getCurrentAuthenticatedUser()
        val endpoint = apiQuotable.endpoint

        if (!apiQuotaService.isRequestAllowed(user, endpoint)) {
            throw com.lcaohoanq.common.exceptions.TooManyRequestsException("Too many requests. Please try again later.")
        }
    }

    // dynamic quota on role, attempt
    @Pointcut("@annotation(dynamicApiQuotable)")
    fun dynamicApiQuotableAnnotation(dynamicApiQuotable: DynamicApiQuotable) {
    }

    @Before("dynamicApiQuotableAnnotation(dynamicApiQuotable)")
    fun checkDynamicApiQuota(joinPoint: JoinPoint, dynamicApiQuotable: DynamicApiQuotable) {
        val user = authService.getCurrentAuthenticatedUser()
        val endpoint = dynamicApiQuotable.endpoint

        val maxRequests = when (user.role) {
            User.Role.MEMBER -> dynamicApiQuotable.memberMaxRequests
            User.Role.STAFF -> dynamicApiQuotable.staffMaxRequests
            User.Role.ADMIN -> dynamicApiQuotable.adminMaxRequests
            null -> {
                throw IllegalStateException("User role is not defined")
            }
        }

        if (!apiQuotaService.isRequestAllowed(user, endpoint, maxRequests)) {
            throw com.lcaohoanq.common.exceptions.TooManyRequestsException("Too many requests. Please try again later.")
        }
    }

    // dynamic quota on role, attempt, custom reset time
    @Before("apiQuotableAnnotation(apiQuotable)")
    fun checkApiQuotaSpecialization(joinPoint: JoinPoint, apiQuotable: ApiQuotable) {
        val user = authService.getCurrentAuthenticatedUser()
        val role = user.role  // Assuming 'role' is part of the User entity

        // Get the maxRequests and resetTime from the roleQuotaConfig map
        val (maxRequests, resetTime) = roleQuotaConfig[role]
            ?: throw IllegalArgumentException("Unknown role: $role")

        if (!apiQuotaService.isRequestAllowed(user, apiQuotable.endpoint, maxRequests, resetTime)) {
            throw com.lcaohoanq.common.exceptions.TooManyRequestsException(
                "Too many requests. Please try again later."
            )
        }
    }
}

