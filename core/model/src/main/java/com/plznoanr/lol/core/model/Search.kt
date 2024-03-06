package com.plznoanr.lol.core.model

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import java.time.format.DateTimeFormatter


data class Search(
    val nickname: Nickname,
    val date: LocalDateTime
) {
    val displayDateTime: String
        get() = date.toJavaLocalDateTime()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
}
