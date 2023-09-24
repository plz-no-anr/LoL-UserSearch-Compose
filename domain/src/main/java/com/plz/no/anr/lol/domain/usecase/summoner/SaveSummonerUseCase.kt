package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class SaveSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val summonerRepository: SummonerRepository
) : BaseUseCase<Summoner, Unit>(coroutineDispatcher) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(parameter: Summoner): Flow<Result<Unit>> =
        summonerRepository.getSummonerList()
            .map { result ->
                result.getOrNull()?.let { summoners ->
                    summoners.find { parameter.name == it.name }
                }
            }.flatMapConcat {
                if (it != null) {
                    summonerRepository.updateSummoner(parameter)
                } else {
                    summonerRepository.insertSummoner(parameter)
                }
            }

}