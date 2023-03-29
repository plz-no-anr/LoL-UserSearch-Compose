package com.plznoanr.domain.usecase.summoner

import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val repository: AppRepository
): BaseUseCase<Unit, List<Summoner>>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<List<Summoner>>> {
        return repository.getSummonerList()
    }

}