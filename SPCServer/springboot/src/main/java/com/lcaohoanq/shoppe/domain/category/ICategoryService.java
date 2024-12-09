package com.lcaohoanq.shoppe.domain.category;

import com.lcaohoanq.shoppe.base.exception.DataAlreadyExistException;
import com.lcaohoanq.shoppe.base.exception.DataNotFoundException;
import java.util.List;

public interface ICategoryService {

    List<CategoryResponse>  createCategory(CategoryDTO categoryDTO) throws DataAlreadyExistException;
    CategoryResponse getById(long id) throws DataNotFoundException;
    List<CategoryResponse> getAllCategories();
    void update(long categoryId, CategoryDTO categoryDTO) throws DataNotFoundException;
    void delete(long id);
    SubcategoryResponse getSubCategory(long subcategoryId) throws DataNotFoundException;
    CreateNewSubcategoryResponse createSubCategory(SubcategoryDTO subcategoryDTO) throws DataNotFoundException, DataAlreadyExistException;

}
