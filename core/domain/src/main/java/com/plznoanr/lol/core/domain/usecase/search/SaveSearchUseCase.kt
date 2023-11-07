package com.plznoanr.lol.core.domain.usecase.search

import com.plznoanr.lol.core.data.repository.SearchRepository
import com.plznoanr.lol.core.model.Search
import javax.inject.Inject

class SaveSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend operator fun invoke(search: Search) {
        searchRepository.upsertSearch(search)
    }
}