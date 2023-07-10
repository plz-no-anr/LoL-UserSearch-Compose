package com.plz.no.anr.lol.domain.usecase.search

import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.domain.repository.SearchRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetSearchUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: SearchRepository
): BaseUseCase<Unit, List<Search>>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<List<Search>>> {
        return repository.getSearchList()
    }

}
