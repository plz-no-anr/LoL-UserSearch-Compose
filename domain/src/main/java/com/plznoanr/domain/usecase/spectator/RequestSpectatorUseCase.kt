package com.plznoanr.domain.usecase.spectator

import com.plznoanr.domain.model.Spectator
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RequestSpectatorUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: AppRepository
): BaseUseCase<String, Spectator>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Spectator>> {
        return appRepository.requestSpectator(parameter)
    }

}