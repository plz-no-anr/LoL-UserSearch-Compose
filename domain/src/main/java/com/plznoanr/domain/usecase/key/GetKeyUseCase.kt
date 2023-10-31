package com.plznoanr.domain.usecase.key

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.data.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKeyUseCase @Inject constructor(
    private val repository: AppRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<Unit, String?>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<String?>> {
        return repository.getApiKey()
    }

}