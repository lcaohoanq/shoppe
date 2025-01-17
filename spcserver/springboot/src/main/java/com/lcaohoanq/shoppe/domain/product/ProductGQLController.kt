package com.lcaohoanq.shoppe.domain.product

import com.lcaohoanq.shoppe.repository.ProductRepository
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller

@Controller
class ProductGQLController(
    private val productRepository: ProductRepository
) {

    @PreAuthorize("permitAll()")
    @QueryMapping(name = "products")
    fun getAll(): List<Product> = productRepository.findAll()

    @PreAuthorize("permitAll()")
    @QueryMapping(name = "product")
    fun getById(id: Long): Product = productRepository.findById(id).orElseThrow();

}