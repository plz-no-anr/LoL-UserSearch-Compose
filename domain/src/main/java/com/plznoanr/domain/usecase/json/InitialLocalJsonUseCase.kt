package com.plznoanr.domain.usecase.json

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 초기 inital시 true
 * else
 * false
 */
class InitialLocalJsonUseCase @Inject constructor(
    @AppDispatchers.Default coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: AppRepository
) : BaseUseCase<Unit, Boolean>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<Boolean>> {
        return appRepository.initLocalJson()
    }

}