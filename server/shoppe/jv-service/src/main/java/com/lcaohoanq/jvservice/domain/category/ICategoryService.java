package com.lcaohoanq.jvservice.domain.category;

import com.lcaohoanq.jvservice.api.PageResponse;
import com.lcaohoanq.jvservice.base.exception.DataAlreadyExistException;
import com.lcaohoanq.jvservice.base.exception.DataNotFoundException;
import com.lcaohoanq.jvservice.domain.category.CategoryPort.CategoryDTO;
import com.lcaohoanq.jvservice.domain.category.CategoryPort.CategoryResponse;
import com.lcaohoanq.jvservice.domain.category.CategoryPort.CreateNewSubcategoryResponse;
import com.lcaohoanq.jvservice.domain.product.ProductPort;
import com.lcaohoanq.jvservice.domain.subcategory.SubcategoryPort.SubcategoryDTO;
import com.lcaohoanq.jvservice.domain.subcategory.SubcategoryPort.SubcategoryResponse;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ICategoryService {

    List<CategoryResponse>  createCategory(CategoryDTO categoryDTO) throws DataAlreadyExistException;
    CategoryResponse getById(long id) throws DataNotFoundException;
    List<CategoryResponse> getAllCategories();
    void update(long categoryId, CategoryDTO categoryDTO) throws DataNotFoundException;
    void delete(long id);
    SubcategoryResponse getSubCategory(long subcategoryId) throws DataNotFoundException;
    CreateNewSubcategoryResponse createSubCategory(SubcategoryDTO subcategoryDTO) throws DataNotFoundException, DataAlreadyExistException;
    PageResponse<ProductPort.ProductResponse> findByCategoryId(Long categoryId, Pageable pageable);
}
