package com.plz.no.anr.lol.domain.usecase.search

import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.domain.repository.SearchRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class SaveSearchUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val searchRepository: SearchRepository
) : BaseUseCase<Search, Unit>(coroutineDispatcher) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(parameter: Search): Flow<Result<Unit>> =
        searchRepository.getSearchList()
            .map { result ->
                result.getOrNull()?.let { searchList ->
                    searchList.find { parameter.name.lowercase() == it.name.lowercase() }
                }
            }.flatMapConcat {
                if (it != null) {
                    searchRepository.updateSearch(parameter)
                } else {
                    searchRepository.insertSearch(parameter)
                }
            }

}
