package com.plznoanr.lol.core.domain.usecase.key

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.data.repository.AppRepository
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