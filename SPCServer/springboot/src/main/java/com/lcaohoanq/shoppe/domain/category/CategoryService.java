package com.lcaohoanq.shoppe.domain.category;

import com.lcaohoanq.shoppe.exception.CategoryAlreadyExistException;
import com.lcaohoanq.shoppe.exception.CategoryNotFoundException;
import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.util.DTOConverter;
import com.lcaohoanq.shoppe.util.PaginationConverter;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService, DTOConverter,
    PaginationConverter {

    private final CategoryRepository categoryRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<CategoryResponse> createCategory(CategoryDTO category)
        throws DataAlreadyExistException {
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

    @Override
    public SubcategoryResponse getSubCategory(long subcategoryId) throws DataNotFoundException {
        Subcategory subcategory = subcategoryRepository.findById(subcategoryId)
            .orElseThrow(() -> new DataNotFoundException("Subcategory not found"));
        return new SubcategoryResponse(subcategory);
    }

    @Override
    public CreateNewSubcategoryResponse createSubCategory(SubcategoryDTO subcategoryDTO)
        throws DataNotFoundException, DataAlreadyExistException {

        Category category = categoryRepository.findById(subcategoryDTO.categoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        if (subcategoryRepository.existsByCategoryIdAndNameIn(category.getId(), subcategoryDTO.name())) {
            throw new DataAlreadyExistException("Subcategory already exist in category: " + category.getId());
        }

        subcategoryDTO.name().forEach(name -> {
            subcategoryRepository.save(
                Subcategory.builder()
                    .name(name)
                    .category(category)
                    .build());
        });

        return new CreateNewSubcategoryResponse(toCategoryResponse(category));
        

    }
}
