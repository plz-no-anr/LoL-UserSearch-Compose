package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class RequestSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: SummonerRepository
): BaseUseCase<String, Summoner>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Summoner>> {
        return appRepository.requestSummoner(parameter)
    }

}