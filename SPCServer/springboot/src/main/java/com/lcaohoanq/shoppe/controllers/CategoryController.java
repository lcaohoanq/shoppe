package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.request.CategoryDTO;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.ApiResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.services.category.ICategoryService;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int limit
    ) {
        return ResponseEntity.ok(categoryService.getAllCategories(PageRequest.of(page, limit,
                                                                                 Sort.by(
                                                                                     "createdAt").ascending())));
    }


    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable int id) {
        return ResponseEntity.ok(
            ApiResponse.<CategoryResponse>builder()
                .message("Get category successfully")
                .statusCode(HttpStatus.OK.value())
                .isSuccess(true)
                .data(categoryService.getById(id))
                .build()
        );
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
        @Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
            ApiResponse.<CategoryResponse>builder()
                .message("Create category successfully")
                .statusCode(HttpStatus.CREATED.value())
                .isSuccess(true)
                .data(categoryService.createCategory(categoryDTO))
                .build()
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
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
    public ResponseEntity<ApiResponse<CategoryResponse>> deleteCategory(@PathVariable int id) {
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
