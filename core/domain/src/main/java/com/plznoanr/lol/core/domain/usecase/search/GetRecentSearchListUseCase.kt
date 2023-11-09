package com.plznoanr.lol.core.domain.usecase.search

import com.plznoanr.lol.core.data.repository.SearchRepository
import com.plznoanr.lol.core.model.Search
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecentSearchListUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
) {

    operator fun invoke(): Flow<List<Search>> {
        return searchRepository.getSearchList().map {
            it.sortedByDescending { search -> search.date }
        }
    }

}
