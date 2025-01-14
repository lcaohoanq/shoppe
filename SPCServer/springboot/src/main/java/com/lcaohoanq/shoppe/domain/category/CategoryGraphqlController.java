package com.lcaohoanq.shoppe.domain.category;

import com.lcaohoanq.shoppe.domain.subcategory.Subcategory;
import com.lcaohoanq.shoppe.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CategoryGraphqlController {

    private final CategoryRepository categoryRepository;
    
    @QueryMapping(name = "getAllCategories")
    @PreAuthorize("permitAll()")
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @SchemaMapping(typeName = "Subcategory")
    @PreAuthorize("permitAll()")
    public Category category(Subcategory subcategory) {
        // The category is already loaded due to JPA relationship
        return subcategory.getCategory();
    }
    
}
