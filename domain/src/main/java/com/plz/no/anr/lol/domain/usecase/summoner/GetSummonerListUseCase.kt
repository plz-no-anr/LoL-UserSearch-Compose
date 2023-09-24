package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetSummonerListUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val summonerRepository: SummonerRepository
): BaseUseCase<Unit, List<Summoner>>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<List<Summoner>>> {
        return summonerRepository.getSummonerList()
    }

}