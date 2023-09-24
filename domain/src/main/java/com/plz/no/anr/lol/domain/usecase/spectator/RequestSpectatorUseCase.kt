package com.plz.no.anr.lol.domain.usecase.spectator

import com.plz.no.anr.lol.domain.model.Spectator
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RequestSpectatorUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val summonerRepository: SummonerRepository
): BaseUseCase<String, Spectator>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Spectator>> {
        return summonerRepository.requestSpectator(parameter)
    }

}