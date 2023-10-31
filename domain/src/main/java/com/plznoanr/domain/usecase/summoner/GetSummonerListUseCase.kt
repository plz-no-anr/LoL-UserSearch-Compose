package com.plznoanr.domain.usecase.summoner

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.model.Summoner
import com.plznoanr.data.repository.SummonerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSummonerListUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher,
): BaseUseCase<Unit, List<Summoner>>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<List<Summoner>>> {
        return summonerRepository.getSummonerList()
    }

}