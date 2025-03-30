package com.lcaohoanq.kotlinbasics.domain.category

import BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "categories")
class Category: BaseEntity() {

    @Id
    val id: Long? = null
    val name: String = ""

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    val subCategories: Set<SubCategory> = emptySet()

}