package com.lcaohoanq.shoppe.domain.product;

import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.domain.product.ProductPort.ProductImageResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final IProductImageService productImageService;

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PageResponse<ProductImageResponse>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        return ResponseEntity.ok(productImageService.getAll(
                PageRequest.of(
                    page, 
                    limit, 
                    Sort.by("created_at").ascending())));
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getByProductId(
        @PathVariable("id") Long productId
    ) throws Exception {
        return ResponseEntity.ok(
            ApiResponse.<List<ProductImageResponse>>builder()
                .message(String.format("Get list product images %s success", productId))
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(productImageService.getByProductId(productId))
                .build()
        );
    }

}
