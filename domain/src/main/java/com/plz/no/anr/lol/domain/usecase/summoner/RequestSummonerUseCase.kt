package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RequestSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: SummonerRepository
): BaseUseCase<String, Unit>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Unit>> {
        return appRepository.requestSummoner(parameter)
    }

}