package com.plznoanr.domain.usecase.search

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.data.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSearchUseCase@Inject constructor(
    private val searchRepository: SearchRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<String, Unit>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Unit>> {
        return searchRepository.deleteSearch(parameter)
    }

}