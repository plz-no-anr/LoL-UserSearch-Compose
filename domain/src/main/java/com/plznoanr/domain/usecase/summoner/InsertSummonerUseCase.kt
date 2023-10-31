package com.plznoanr.domain.usecase.summoner

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.model.Summoner
import com.plznoanr.data.repository.SummonerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher,
): BaseUseCase<Summoner, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Summoner): Flow<Result<Unit>> {
        return summonerRepository.insertSummoner(parameter)
    }

}