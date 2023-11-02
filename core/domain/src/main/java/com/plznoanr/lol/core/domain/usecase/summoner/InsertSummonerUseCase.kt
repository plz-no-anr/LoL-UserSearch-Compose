package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.data.repository.SummonerRepository
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