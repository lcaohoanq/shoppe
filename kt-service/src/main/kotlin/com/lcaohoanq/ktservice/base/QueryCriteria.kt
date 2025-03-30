package com.lcaohoanq.ktservice.base

import com.lcaohoanq.ktservice.utils.SortOrder
import com.lcaohoanq.ktservice.utils.Sortable
import com.lcaohoanq.ktservice.utils.SortableField


data class QueryCriteria<T : SortableField>(
    val search: String = "",
    val sortBy: T = Sortable.UserSortField.ID as T,
    val sortOrder: SortOrder = SortOrder.ASC
)