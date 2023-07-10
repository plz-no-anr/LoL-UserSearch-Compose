package com.plz.no.anr.lol.domain.usecase.search

import com.plz.no.anr.lol.domain.repository.SearchRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class DeleteAllSearchUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: SearchRepository
): BaseUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return appRepository.deleteSearchAll()
    }

}