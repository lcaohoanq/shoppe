package com.lcaohoanq.productservice.domain.category

import BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
@Table(name = "subcategories")
class SubCategory: BaseEntity(), Comparable<SubCategory> {

    @Id
    val id: Long? = null
    val name: String? = null

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore
    val category: Category = Category()

    override fun compareTo(other: SubCategory): Int {
        return compareValues(this.id, other.id)
    }

}