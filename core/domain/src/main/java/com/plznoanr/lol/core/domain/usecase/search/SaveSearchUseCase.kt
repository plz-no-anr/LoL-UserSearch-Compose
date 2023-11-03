package com.plznoanr.lol.core.domain.usecase.search

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.model.Search
import com.plznoanr.lol.core.data.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<Search, Unit>(coroutineDispatcher) {

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
