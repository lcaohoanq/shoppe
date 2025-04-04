package com.lcaohoanq.assetservice

import com.lcaohoanq.ktservice.custom.annotations.DynamicApiQuotable
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@RestController
@Tag(name = "Asset", description = "Asset API")
@RequestMapping("\${api.prefix}/assets")
class AssetController() {

    @Value("\${spring.servlet.multipart.location}")
    private lateinit var imageDirectory: String

    @GetMapping("/images/{filename:.+}")
    @DynamicApiQuotable(endpoint = "/images/{filename:.+}", memberMaxRequests = 5, staffMaxRequests = 10, adminMaxRequests = 15)
    fun serveImage(
        @PathVariable @Parameter(
            description = "Example image",
            required = true,
            content = [Content(
                examples = arrayOf(
                    ExampleObject(
                        value = "2dc73f81-2a42-4924-a580-76209df6a4db_DSCF1023.JPG",
                        description = "The name of the image file"
                    )
                )
            )]
        ) filename: String
    ): ResponseEntity<Resource> {
        val filePath = Paths.get(imageDirectory).resolve(filename).normalize()

        val resource = UrlResource(filePath.toUri())

        if (!resource.exists() || !resource.isReadable) {
            throw IOException("File not found or not readable: $filename")
        }

        // Automatically determine the content type
        val contentType = Files.probeContentType(filePath) ?: "application/octet-stream"

        val headers = HttpHeaders().apply {
            add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"$filename\"")
        }

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .headers(headers)
            .body(resource)
    }

}