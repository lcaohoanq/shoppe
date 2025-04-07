package com.lcaohoanq.productservice.domain.category

import com.lcaohoanq.productservice.repository.CategoryRepository
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.SchemaMapping
import org.springframework.stereotype.Controller

@Controller
class CategoryGraphqlController(
    private val categoryRepository: CategoryRepository
) {

    @QueryMapping(name = "getAllCategories")
    fun getAll(): List<Category> = categoryRepository.findAll()

    // The category is already loaded due to JPA relationship
    @SchemaMapping(typeName = "Subcategory")
    fun category(subcategory: SubCategory): Category = subcategory.category

}
