package com.lcaohoanq.productservice.domain.category

import BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "categories")
data class Category(
    @Id
    val id: Long? = null,
    val name: String = "",

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val subCategories: Set<SubCategory> = emptySet()
) : BaseEntity() {


}