package com.lcaohoanq.jvservice.domain.category;

import com.lcaohoanq.jvservice.api.PageResponse;
import com.lcaohoanq.jvservice.base.exception.DataAlreadyExistException;
import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.domain.category.CategoryPort.CategoryDTO;
import com.lcaohoanq.jvservice.domain.category.CategoryPort.CategoryResponse;
import com.lcaohoanq.jvservice.domain.category.CategoryPort.CreateNewSubcategoryResponse;
import com.lcaohoanq.jvservice.domain.category.ICategoryService;import com.lcaohoanq.jvservice.domain.product.Product;
import com.lcaohoanq.jvservice.domain.product.ProductPort;
import com.lcaohoanq.jvservice.repository.ProductRepository;
import com.lcaohoanq.jvservice.domain.product.ProductPort.ProductResponse;
import com.lcaohoanq.jvservice.domain.subcategory.Subcategory;
import com.lcaohoanq.jvservice.domain.subcategory.SubcategoryPort.SubcategoryDTO;
import com.lcaohoanq.jvservice.domain.subcategory.SubcategoryPort.SubcategoryResponse;
import com.lcaohoanq.jvservice.repository.CategoryRepository;
import com.lcaohoanq.jvservice.repository.SubcategoryRepository;
import com.lcaohoanq.jvservice.exception.CategoryAlreadyExistException;
import com.lcaohoanq.jvservice.exception.CategoryNotFoundException;
import com.lcaohoanq.jvservice.mapper.CategoryMapper;
import com.lcaohoanq.jvservice.mapper.ProductMapper;
import com.lcaohoanq.jvservice.util.PaginationConverter;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class CategoryService implements ICategoryService, PaginationConverter {

    CategoryRepository categoryRepository;
    SubcategoryRepository subcategoryRepository;
    ProductRepository productRepository;
    CategoryMapper categoryMapper;
    ProductMapper productMapper;

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
            .map(name -> categoryMapper.toCategoryResponse(categoryRepository.findByName(name).get()))
            .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse getById(long id) throws DataNotFoundException {
        Category category = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException("Category not found"));

        return categoryMapper.toCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
            .map(categoryMapper::toCategoryResponse)
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

        return new CreateNewSubcategoryResponse(categoryMapper.toCategoryResponse(category));
        

    }

    @Override
    public PageResponse<ProductPort.ProductResponse> findByCategoryId(Long categoryId, Pageable pageable) {
        if(getById(categoryId) == null){
            throw new DataNotFoundException("Category not found");
        }
        Page<Product> productPage = productRepository.findByCategoryId(categoryId, pageable);
        return mapPageResponse(
            productPage,
            pageable,
            productMapper::toProductResponse,
            "Get products by category successfully",
            HashSet::new
        );
    }
}
