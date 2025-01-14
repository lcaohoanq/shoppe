package com.lcaohoanq.shoppe.mapper;

import com.lcaohoanq.shoppe.domain.category.Category;
import com.lcaohoanq.shoppe.domain.category.CategoryPort.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "subcategories", source = "subcategories")
    CategoryResponse toCategoryResponse(Category category);
}