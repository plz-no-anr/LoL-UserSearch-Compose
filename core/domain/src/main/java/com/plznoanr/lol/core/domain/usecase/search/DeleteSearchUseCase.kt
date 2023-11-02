package com.plznoanr.lol.core.domain.usecase.search

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.data.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<String, Unit>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Unit>> {
        return searchRepository.deleteSearch(parameter)
    }

}