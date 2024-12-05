package com.lcaohoanq.shoppe.services.category;

import com.lcaohoanq.shoppe.dtos.request.CategoryDTO;
import com.lcaohoanq.shoppe.dtos.responses.CategoryResponse;
import com.lcaohoanq.shoppe.dtos.responses.base.PageResponse;
import com.lcaohoanq.shoppe.exceptions.base.DataAlreadyExistException;
import com.lcaohoanq.shoppe.exceptions.base.DataNotFoundException;
import java.util.List;
import org.springframework.data.domain.PageRequest;

public interface ICategoryService {

    CategoryResponse createCategory(CategoryDTO categoryDTO) throws DataAlreadyExistException;
    CategoryResponse getById(long id) throws DataNotFoundException;
    List<CategoryResponse> getAllCategories();
    void update(long categoryId, CategoryDTO categoryDTO) throws DataNotFoundException;
    void delete(long id);

}
