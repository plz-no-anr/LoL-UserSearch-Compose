package com.plznoanr.lol.core.common.model

data class Paging(
    val page: Int = 1,
    val size: Int = 20,
    val hasNext: Boolean = true,
) {
    fun next() = copy(
        page = page + 1
    )

    fun prev() = copy(
        page = page - 1
    )

}