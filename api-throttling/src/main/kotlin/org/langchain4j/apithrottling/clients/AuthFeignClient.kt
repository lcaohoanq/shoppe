package org.langchain4j.apithrottling.clients

import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.common.dto.UserPort
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PatchMapping

@FeignClient(name = "auth-service")
interface AuthFeignClient {

    @PatchMapping("/api/v1/users/details")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBER', 'ROLE_STAFF')")
    fun getCurrentAuthenticatedUser(): ResponseEntity<MyApiResponse<UserPort.UserResponse>>

}