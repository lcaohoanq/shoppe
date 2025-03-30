package com.lcaohoanq.ktservice.domain.category

import BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "categories")
class Category: BaseEntity() {

    @Id
    val id: Long? = null
    val name: String = ""

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val subCategories: Set<SubCategory> = emptySet()

}