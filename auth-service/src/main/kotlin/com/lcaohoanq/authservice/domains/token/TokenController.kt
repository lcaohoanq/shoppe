package com.lcaohoanq.authservice.domains.token

import com.lcaohoanq.common.apis.MyApiResponse
import com.lcaohoanq.common.dto.TokenPort
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("\${api.prefix}/tokens")
@Tag(name = "Token", description = "Token management")
class TokenController(
    private val tokenService: ITokenService
) {

    @GetMapping("/all")
    fun getAllTokens(): ResponseEntity<MyApiResponse<List<TokenPort.TokenResponse>>> {
        return ResponseEntity.ok(
            MyApiResponse(
                message = "Get all tokens successfully",
                data = tokenService.getAll()
            )
        )
    }

}