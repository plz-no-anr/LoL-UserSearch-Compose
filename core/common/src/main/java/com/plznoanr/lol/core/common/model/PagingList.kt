package com.plznoanr.lol.core.common.model

data class PagingList<T>(
    val data: List<T>,
    val page: Int = 1,
    val size: Int = 20,
    val hasNext: Boolean = true,
)