package com.lcaohoanq.shoppe.services.category;

import com.lcaohoanq.shoppe.dtos.request.CategoryDTO;
import com.lcaohoanq.shoppe.dtos.request.SubcategoryDTO;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.CreateNewSubcategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.SubcategoryResponse;
import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
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
