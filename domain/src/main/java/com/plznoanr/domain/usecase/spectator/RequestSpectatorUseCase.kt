package com.plznoanr.domain.usecase.spectator

import com.plznoanr.domain.model.Spectator
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class RequestSpectatorUseCase(
    private val appRepository: AppRepository
): BaseUseCase<String, Spectator>() {

    override fun execute(parameter: String): Flow<Result<Spectator>> {
        return appRepository.requestSpectator(parameter)
    }

}