package com.plznoanr.lol.core.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter


data class Search(
    val name: String,
    val date: LocalDateTime,
    val isBookmark: Boolean = false,
) {
    val displayDateTime: String
        get() = date.toJavaLocalDateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
