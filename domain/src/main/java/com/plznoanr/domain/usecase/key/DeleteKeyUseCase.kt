package com.plznoanr.domain.usecase.key

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteKeyUseCase @Inject constructor(
    private val repository: AppRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, Unit>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return repository.deleteApiKey()
    }

}