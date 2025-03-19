package com.lcaohoanq.shoppe.domain.asset;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Assets", description = "APIs for serving static assets")
@RequestMapping("${api.prefix}/assets")
@Slf4j
public class AssetController {

    @Value("${servlet.multipart.location:uploads}")
    private String imageDirectory; // The folder where images are stored

    @GetMapping("/images/{filename:.+}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Resource> serveImage(
        @PathVariable
        @Parameter(description = "Example image", required =
            true,
            content = @Content(examples = @ExampleObject(
                value = "2dc73f81-2a42-4924-a580-76209df6a4db_DSCF1023.JPG",
                description = "The name of the image file"
            ))
        )
        String filename) throws IOException {
        Path filePath = Paths.get(imageDirectory).resolve(filename).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new IOException("File not found: " + filename);
        }

        if (!resource.isReadable()) {
            throw new IOException("File is not readable: " + filename);
        }

        // Automatically determine the content type
        String contentType = Files.probeContentType(filePath);
        if (contentType == null) {
            contentType = "application/octet-stream"; // Fallback to binary stream if unknown
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .headers(headers)
            .body(resource);

    }

}
