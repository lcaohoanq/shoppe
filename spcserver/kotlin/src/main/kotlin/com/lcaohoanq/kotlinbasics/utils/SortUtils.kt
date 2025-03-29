package com.lcaohoanq.kotlinbasics.utils

enum class SortOrder {
    ASC,
    DESC
}

interface SortableField {
    val field: String
}

data class SortCriterion<T : SortableField>(
    val sortBy: T,
    val order: SortOrder
)

interface Sortable {

    enum class UserSortField(override val field: String) : SortableField {
        ID("id"),
        ROLE("role"),
        PHONE("phone"),
        USER_NAME("username"),
        EMAIL("email"),
        CREATED_AT("createdAt")
    }

    enum class ProductSortField(override val field: String) : SortableField {
        ID("id"),
        NAME("name"),
        PRICE("price"),
        QUANTITY("quantity"),
        CREATED_AT("createdAt")
    }
}