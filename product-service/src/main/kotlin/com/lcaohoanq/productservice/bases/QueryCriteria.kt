package com.lcaohoanq.ktservice.bases

import com.lcaohoanq.common.utils.SortOrder
import com.lcaohoanq.common.utils.Sortable
import com.lcaohoanq.common.utils.SortableField


data class QueryCriteria<T : SortableField>(
    val search: String = "",
    val sortBy: T = Sortable.UserSortField.ID as T,
    val sortOrder: SortOrder = SortOrder.ASC
)