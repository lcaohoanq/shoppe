package com.lcaohoanq.shoppe.services.category;

import com.lcaohoanq.shoppe.dtos.request.CategoryDTO;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.exceptions.CategoryAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.CategoryNotFoundException;
import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.repositories.CategoryRepository;
import com.lcaohoanq.shoppe.utils.DTOConverter;
import com.lcaohoanq.shoppe.utils.PaginationConverter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService, DTOConverter,
    PaginationConverter {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> createCategory(CategoryDTO category) throws DataAlreadyExistException {
        List<String> categories = category.name();

        //check if category already exist
        if (categoryRepository.existsByNameIn(categories)) {
            throw new CategoryAlreadyExistException("Category already exist");
        }
        
        categories.forEach(name -> {
            Category newCategory = new Category();
            newCategory.setName(name);
            categoryRepository.save(newCategory);
        });
    
        return categories.stream()
            .map(name -> toCategoryResponse(categoryRepository.findByName(name).get()))
            .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(long id) throws DataNotFoundException {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
       return categoryRepository.findAll().stream()
           .map(this::toCategoryResponse)
           .sorted((o1, o2) -> (int) (o1.id() - o2.id()))
           .toList();
    }

    @Override
    public void update(long categoryId, CategoryDTO category)
        throws DataNotFoundException {

        Category existingCategory = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        existingCategory.setName(String.valueOf(category.name()));
        categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(long id) {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException(
                String.format("Category with id: %d not found", id)
            ));

        categoryRepository.delete(category);
    }
}
