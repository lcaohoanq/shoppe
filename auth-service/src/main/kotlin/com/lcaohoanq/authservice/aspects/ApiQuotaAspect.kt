package com.lcaohoanq.authservice.aspects

import com.lcaohoanq.authservice.configs.roleQuotaConfig
import com.lcaohoanq.authservice.domains.auth.IAuthService
import com.lcaohoanq.authservice.domains.quota.ApiQuotaService
import com.lcaohoanq.common.annotations.ApiQuotable
import com.lcaohoanq.common.annotations.DynamicApiQuotable
import com.lcaohoanq.common.enums.UserEnum
import com.lcaohoanq.commonspring.utils.unwrap
import mu.KotlinLogging
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class ApiQuotaAspect(
    private val apiQuotaService: ApiQuotaService,
    private val authService: IAuthService
) {

    private val log = KotlinLogging.logger {}

    // hard code quota
    @Pointcut("@annotation(apiQuotable)")
    fun apiQuotableAnnotation(apiQuotable: ApiQuotable) {
    }

    @Before("apiQuotableAnnotation(apiQuotable)")
    fun checkApiQuota(joinPoint: JoinPoint, apiQuotable: ApiQuotable) {
        val user = authService.getCurrentAuthenticatedUser()

        val endpoint = apiQuotable.endpoint

        if (!apiQuotaService.isRequestAllowed(user.id!!, endpoint)) {
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
            UserEnum.Role.MEMBER -> dynamicApiQuotable.memberMaxRequests
            UserEnum.Role.STAFF -> dynamicApiQuotable.staffMaxRequests
            UserEnum.Role.ADMIN -> dynamicApiQuotable.adminMaxRequests
            else -> throw IllegalArgumentException("Unknown role: ${user.role}")
        }

        if (!apiQuotaService.isRequestAllowed(user.id!!, endpoint, maxRequests)) {
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

        if (!apiQuotaService.isRequestAllowed(
                user.id!!,
                apiQuotable.endpoint,
                maxRequests,
                resetTime
            )
        ) {
            throw com.lcaohoanq.common.exceptions.TooManyRequestsException(
                "Too many requests. Please try again later."
            )
        }
    }
}

