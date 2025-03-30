package com.lcaohoanq.ktservice.domain.experiment

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@RestController
@RequestMapping("\${api.prefix}/experiments")
@Tag(name = "Experiment", description = "Experiment Controller")
class ExperimentController(
    private val webClient: WebClient
) {

    // Demo for reactive programming
    @Operation(
        summary = "Get avatar from DiceBear API",
        description = "Demo for reactive programming"
    )
    @PreAuthorize("permitAll()")
    @GetMapping("/mock-avatar")
    fun getAvatar(
        @Schema(description = "Seed for generating avatar", example = "Hoang")
        @RequestParam seed: String): Mono<ResponseEntity<ByteArray>> {
        return fetchAvatar(seed)
    }

    private fun fetchAvatar(seed: String): Mono<ResponseEntity<ByteArray>> {
        val url = "https://api.dicebear.com/9.x/adventurer/svg?seed=$seed"

        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(ByteArray::class.java)
            .map { byteArray ->
                // Create headers
                val headers = HttpHeaders().apply {
                    // Set the content type for SVG
                    add(HttpHeaders.CONTENT_TYPE, "image/svg+xml")
                    // Set content disposition to display inline
                    add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"avatar-$seed.svg\"")
                }

                // Return the response with the headers and body
                ResponseEntity.ok()
                    .headers(headers)
                    .body(byteArray)
            }
    }

}