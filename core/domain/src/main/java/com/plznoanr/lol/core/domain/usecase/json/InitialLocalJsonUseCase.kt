package com.plznoanr.lol.core.domain.usecase.json

import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.data.repository.AppRepository
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