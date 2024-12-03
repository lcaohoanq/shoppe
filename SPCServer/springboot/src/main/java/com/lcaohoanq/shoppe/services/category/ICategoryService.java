package com.lcaohoanq.shoppe.services.category;

import com.lcaohoanq.shoppe.dtos.CategoryDTO;
import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import com.lcaohoanq.shoppe.models.Category;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import org.springframework.data.domain.PageRequest;

public interface ICategoryService {

    Category createCategory(CategoryDTO category) throws DataAlreadyExistException;
    Category getById(long id) throws DataNotFoundException;
    PageResponse<CategoryResponse> getAllCategories(PageRequest pageRequest);
    void update(long categoryId, CategoryDTO category) throws DataNotFoundException;
    void delete(long id);

}
