package com.plznoanr.domain.usecase.summoner

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.SummonerRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.model.Summoner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RequestSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher,
): BaseUseCase<String, Summoner>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Summoner>> {
        return summonerRepository.requestSummoner(parameter)
    }

}