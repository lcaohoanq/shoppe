package com.lcaohoanq.shoppe.domain.category

import com.lcaohoanq.shoppe.domain.subcategory.Subcategory
import com.lcaohoanq.shoppe.repository.CategoryRepository
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller

@Controller
class CategoryGraphqlController(
    private val categoryRepository: CategoryRepository
) {

    @PreAuthorize("permitAll()")
    @QueryMapping(name = "getAllCategories")
    fun getAll(): List<Category> = categoryRepository.findAll()

    // The category is already loaded due to JPA relationship
    @SchemaMapping(typeName = "Subcategory")
    @PreAuthorize("permitAll()")
    fun category(subcategory: Subcategory): Category = subcategory.category

}
