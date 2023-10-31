package com.plznoanr.domain.usecase.search

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.SearchRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return searchRepository.deleteSearchAll()
    }

}