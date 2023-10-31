package com.plznoanr.domain.usecase.spectator

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.SummonerRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.model.Spectator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestSpectatorUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<String, Spectator>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Spectator>> {
        return summonerRepository.requestSpectator(parameter)
    }

}