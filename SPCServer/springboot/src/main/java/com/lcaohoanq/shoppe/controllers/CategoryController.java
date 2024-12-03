package com.lcaohoanq.shoppe.controllers;

import com.lcaohoanq.shoppe.components.LocalizationUtils;
import com.lcaohoanq.shoppe.dtos.CategoryDTO;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.exceptions.MethodArgumentNotValidException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.services.category.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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
public class CategoryController {

    private final ICategoryService categoryService;
    private final LocalizationUtils localizationUtils;

    @GetMapping("")
    public ResponseEntity<PageResponse<CategoryResponse>> getAllCategories(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "0") int limit
    ) {
        try {
            return ResponseEntity.ok(categoryService.getAllCategories(PageRequest.of(page, limit)));
        } catch (Exception e) {
            throw new DataNotFoundException(
                localizationUtils.getLocalizedMessage("category.get_all_failed"));
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable int id) {
        Category category = categoryService.getById(id);
        CategoryResponse response = new CategoryResponse(category.getId(), category.getName());
        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> createCategory(
        @Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result
    ) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        try {

            Category category = categoryService.createCategory(categoryDTO);

            CategoryResponse response = new CategoryResponse(category.getId(), category.getName());

            categoryService.createCategory(categoryDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            throw new RuntimeException(
                localizationUtils.
                    getLocalizedMessage("category.create_category.create_failed"));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> updateCategory(
        @PathVariable int id,
        @Valid @RequestBody CategoryDTO categoryDTO,
        BindingResult result) {

        if (result.hasErrors()) {
            throw new MethodArgumentNotValidException(result);
        }

        try {
            categoryService.update(id, categoryDTO);
            return ResponseEntity.ok().body(localizationUtils.getLocalizedMessage(
                "category.update_category.update_successfully"));
        } catch (Exception e) {
            throw new RuntimeException(
                localizationUtils.
                    getLocalizedMessage("category.update_category.update_failed"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        try {
            categoryService.delete(id);
            return ResponseEntity.ok(localizationUtils.getLocalizedMessage(
                "category.delete_category.delete_successfully", id));
        } catch (Exception e) {
            throw new RuntimeException(
                localizationUtils.
                    getLocalizedMessage("category.delete_category.delete_failed"));
        }
    }


}
