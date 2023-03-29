package com.plznoanr.domain.usecase.summoner

import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: AppRepository
): BaseUseCase<Summoner, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Summoner): Flow<Result<Unit>> {
        return appRepository.insertSummoner(parameter)
    }

}