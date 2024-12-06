package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.dtos.responses.ProductImageResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.services.productimage.IProductImageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final IProductImageService productImageService;

    @GetMapping("")
    public ResponseEntity<PageResponse<ProductImageResponse>> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) throws Exception {

        return ResponseEntity.ok(
            productImageService.getAll(PageRequest.of(page, limit)));
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ApiResponse<List<ProductImageResponse>>> getByProductId(
        @PathVariable("id") Long productId
    ) throws Exception {
        return ResponseEntity.ok(
            ApiResponse.<List<ProductImageResponse>>builder()
                .message("Get list product success")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(productImageService.getByProductId(productId))
                .build()
        );
    }

}
