package com.lcaohoanq.authservice.domains.settings

import com.lcaohoanq.authservice.extension.toUserSettingsResponse
import com.lcaohoanq.authservice.repositories.UserSettingsRepository
import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.authservice.domains.user.UserPort
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/user-settings")
@Tag(name = "User settings", description = "User settings APIs")
class UserSettingsController(
    private val userSettingsRepository: UserSettingsRepository
) {

    @GetMapping("")
    @Operation(summary = "Get settings of user by id")
    fun getDetailSettingsOfUser(@RequestParam id: Long): ResponseEntity<MyApiResponse<UserPort.UserSettingsResponse>> {
        return ResponseEntity.ok(
            MyApiResponse(
                message = "User settings of $id has been successfully retrieved",
                data = userSettingsRepository.findByUserId(id).toUserSettingsResponse()
            )
        )
    }

}