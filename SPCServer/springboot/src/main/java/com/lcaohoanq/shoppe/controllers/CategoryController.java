package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.request.CategoryDTO;
import com.lcaohoanq.shoppe.dtos.request.SubcategoryDTO;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.SubcategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.services.category.ICategoryService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.MessageKey;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController implements DTOConverter {

    private final ICategoryService categoryService;
    private final LocalizationUtils localizationUtils;

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll(
    ) {
        return ResponseEntity.ok(
            ApiResponse.<List<CategoryResponse>>builder()
                .message("Get all categories successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(categoryService.getAllCategories())
                .build());
    }


    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable int id) {
        return ResponseEntity.ok(
            ApiResponse.<CategoryResponse>builder()
                .message("Get category successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(categoryService.getById(id))
                .build()
        );
    }
    
    @GetMapping("/sub/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<SubcategoryResponse>> getSubCategories(
        @PathVariable int id
    ) {
        return ResponseEntity.ok(
            ApiResponse.<SubcategoryResponse>builder()
                .message("Get sub categories successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(categoryService.getSubCategories(id))
                .build()
        );
    }
    
    @PostMapping("/sub")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<SubcategoryResponse>> createSubCategory(
        @Valid @RequestBody SubcategoryDTO subcategoryDTO,
        BindingResult result
    ) {
        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<SubcategoryResponse>builder()
                .message("Create sub category successfully")
                .statusCode(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(categoryService.createSubCategory(subcategoryDTO))
                .build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> create(
        @Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<List<CategoryResponse>>builder()
                .message("Create category successfully")
                .statusCode(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(categoryService.createCategory(categoryDTO))
                .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(
        @PathVariable int id,
        @Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        categoryService.update(id, categoryDTO);
        return ResponseEntity.ok().body(
            ApiResponse.<CategoryResponse>builder()
                .message(localizationUtils.getLocalizedMessage(
                    "category.update_category.update_successfully"))
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(categoryService.getById(id))
                .build()
        );

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<CategoryResponse>> delete(@PathVariable int id) {
        categoryService.delete(id);
        return ResponseEntity.ok(
            ApiResponse.<CategoryResponse>builder()
                .message(localizationUtils.getLocalizedMessage(
                    "category.delete_category.delete_successfully", id))
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .build()
        );
    }


}
