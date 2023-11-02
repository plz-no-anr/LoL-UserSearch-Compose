package com.plznoanr.lol.core.domain.usecase.spectator

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.model.Spectator
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