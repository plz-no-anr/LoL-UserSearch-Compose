package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: SummonerRepository
): BaseUseCase<Summoner, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Summoner): Flow<Result<Unit>> {
        return appRepository.insertSummoner(parameter)
    }

}