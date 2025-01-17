package com.lcaohoanq.shoppe.domain.inventory;

import com.lcaohoanq.shoppe.api.ApiResponse;
import com.lcaohoanq.shoppe.domain.product.IProductService;
import com.lcaohoanq.shoppe.domain.product.Product;
import com.lcaohoanq.shoppe.exception.MethodArgumentNotValidException;
import jakarta.validation.Valid;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/warehouses")
@RequiredArgsConstructor
@Slf4j
public class WarehouseController {

    private final IWarehouseService warehouseService;
    private final IProductService productService;

    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<List<WarehouseResponse>>> getAll() {
        return ResponseEntity.ok().body(
            ApiResponse.<List<WarehouseResponse>>builder()
                .message("Get all warehouses successfully")
                .data(warehouseService.findAll())
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<WarehouseResponse>> getById(
        @PathVariable("id") Long id
    ) {
        
        return ResponseEntity.ok().body(
            ApiResponse.<WarehouseResponse>builder()
                .message("Get warehouse by id successfully")
                .data(warehouseService.findById(id))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
        
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<WarehouseResponse>> create(
        @Valid @RequestBody WarehouseDTO request,
        BindingResult result
    ) {
        
        if(result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        
        return ResponseEntity.ok().body(
            ApiResponse.<WarehouseResponse>builder()
                .message("Create warehouse successfully")
                .data(warehouseService.create(request))
                .isSuccess(true)
                .statusCode(HttpStatus.CREATED.value())
                .build()
        );
    }
    
    @GetMapping("/{id}/products")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<HashSet<Product>>> getProductsByWarehouseId(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(
            ApiResponse.<HashSet<Product>>builder()
                .message("Get products by warehouse id successfully")
                .data(productService.findByWarehouseId(id))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }
    
    @GetMapping("/{id}/count-products")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<Long>> countProductsByWarehouseId(
        @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok().body(
            ApiResponse.<Long>builder()
                .message("Count products by warehouse id successfully")
                .data(productService.countByWarehouseId(id))
                .isSuccess(true)
                .statusCode(HttpStatus.OK.value())
                .build()
        );
    }


}
