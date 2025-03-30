package com.lcaohoanq.jvservice.mapper;

import com.lcaohoanq.jvservice.domain.category.Category;
import com.lcaohoanq.jvservice.domain.category.CategoryPort.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "subcategories", source = "subcategories")
    CategoryResponse toCategoryResponse(Category category);
}