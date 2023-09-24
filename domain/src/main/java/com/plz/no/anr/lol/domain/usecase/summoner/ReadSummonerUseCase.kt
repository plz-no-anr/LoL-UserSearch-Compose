package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.model.Search
import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import com.plz.no.anr.lol.domain.usecase.search.SaveSearchUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class ReadSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val summonerRepository: SummonerRepository,
    private val saveSearchUseCase: SaveSearchUseCase,
    private val saveSummonerUseCase: SaveSummonerUseCase
) : BaseUseCase<String, Summoner>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Summoner>> =
        summonerRepository.requestSummoner(parameter)
            .onEach {
                saveSearchUseCase(
                    Search(
                        name = it.getOrThrow().name,
                        date = System.currentTimeMillis().toString()
                    )
                ).first()
                saveSummonerUseCase(it.getOrThrow()).first()
            }.map {
                if (it.isSuccess) {
                    it
                } else {
                    summonerRepository.getSummoner(parameter).first()
                }
            }

}