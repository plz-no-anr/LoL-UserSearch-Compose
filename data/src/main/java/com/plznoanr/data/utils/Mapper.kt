package com.plznoanr.data.utils

import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.domain.model.Search


fun Search.toEntity() = SearchEntity(
    name = name,
    date = date
)