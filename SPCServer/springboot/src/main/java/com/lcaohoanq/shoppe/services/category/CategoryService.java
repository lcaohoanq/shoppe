package com.lcaohoanq.shoppe.services.category;

import com.lcaohoanq.shoppe.dtos.CategoryDTO;
import com.lcaohoanq.shoppe.exceptions.CategoryAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.CategoryNotFoundException;
import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.metadata.PaginationMeta;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.repositories.CategoryRepository;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;
    private final DTOConverter dtoConverter;

    @Override
    public Category createCategory(CategoryDTO category) throws DataAlreadyExistException {

        categoryRepository.findByName(category.name()).ifPresent(c -> {
            throw new CategoryAlreadyExistException("Category already exist");
        });

        Category newCategory = Category.builder()
            .name(category.name())
            .build();

        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getById(long id) throws DataNotFoundException {
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found"));
    }

    @Override
    public PageResponse<CategoryResponse> getAllCategories(PageRequest pageRequest) {
        Page<Category> categoriesPage = categoryRepository.findAll(pageRequest);

        List<CategoryResponse> categoryResponses = categoriesPage.getContent().stream()
            .map(dtoConverter::toCategoryResponse)
            .collect(Collectors.toList());

        return PageResponse.<CategoryResponse>pageBuilder()
            .data(categoryResponses)
            .pagination(PaginationMeta.builder()
                            .totalPages(categoriesPage.getTotalPages())
                            .totalItems(categoriesPage.getTotalElements())
                            .currentPage(pageRequest.getPageNumber())
                            .pageSize(pageRequest.getPageSize())
                            .build())
            .statusCode(200)
            .isSuccess(true)
            .message("Categories fetched successfully")
            .build();
    }

    @Override
    public void update(long categoryId, CategoryDTO category)
        throws DataNotFoundException {

        Category existingCategory = getById(categoryId);
        existingCategory.setName(category.name());
        categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(long id) {

        categoryRepository.deleteById(id);

    }
}
