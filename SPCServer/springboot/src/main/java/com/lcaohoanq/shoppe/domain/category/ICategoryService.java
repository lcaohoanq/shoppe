package com.lcaohoanq.shoppe.domain.category;

import com.lcaohoanq.shoppe.api.PageResponse;
import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import com.lcaohoanq.shoppe.domain.product.Product;
import com.lcaohoanq.shoppe.domain.product.ProductResponse;
import com.lcaohoanq.shoppe.domain.subcategory.CreateNewSubcategoryResponse;
import com.lcaohoanq.shoppe.domain.subcategory.SubcategoryDTO;
import com.lcaohoanq.shoppe.domain.subcategory.SubcategoryResponse;
import java.util.HashSet;
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
    PageResponse<ProductResponse> findByCategoryId(Long categoryId, Pageable pageable);
}
