package com.lcaohoanq.kotlinbasics.base

import com.lcaohoanq.kotlinbasics.utils.SortOrder
import com.lcaohoanq.kotlinbasics.utils.Sortable
import com.lcaohoanq.kotlinbasics.utils.SortableField


data class QueryCriteria<T : SortableField>(
    val search: String = "",
    val sortBy: T = Sortable.UserSortField.ID as T,
    val sortOrder: SortOrder = SortOrder.ASC
)