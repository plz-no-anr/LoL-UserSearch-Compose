package com.plz.no.anr.lol.domain.usecase.json

import com.plz.no.anr.lol.domain.repository.AppRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

/**
 * 초기 inital시 true
 * else
 * false
 */
class InitialLocalJsonUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
) : BaseUseCase<Unit, Boolean>(coroutineDispatcher) {
    override fun execute(parameter: Unit): Flow<Result<Boolean>> {
        return repository.initLocalJson()
    }

}