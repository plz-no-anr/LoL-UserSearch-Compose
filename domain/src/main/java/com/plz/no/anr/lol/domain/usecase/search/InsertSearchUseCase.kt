package com.plz.no.anr.lol.domain.usecase.search

import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.domain.repository.SearchRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertSearchUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: SearchRepository
): BaseUseCase<Search, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Search): Flow<Result<Unit>> {
        return appRepository.insertSearch(parameter)
    }

}
