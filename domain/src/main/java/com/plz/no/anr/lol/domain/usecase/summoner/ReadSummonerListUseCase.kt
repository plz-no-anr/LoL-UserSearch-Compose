package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class ReadSummonerListUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val summonerRepository: SummonerRepository,
    private val readSummonerUseCase: ReadSummonerUseCase
) : BaseUseCase<Unit, List<Summoner>>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<List<Summoner>>> =
        summonerRepository.getSummonerList()
            .map { result ->
                val summonerList = result.getOrThrow()
                val list = summonerList.map { summoner ->
                    readSummonerUseCase(summoner.name).first().getOrThrow()
                }
                Result.success(list)
            }
}