package com.plznoanr.domain.usecase.summoner

import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class InsertSummonerUseCase(
    private val appRepository: AppRepository
): BaseUseCase<Summoner, Unit>() {

    override fun execute(parameter: Summoner): Flow<Result<Unit>> {
        return appRepository.insertSummoner(parameter)
    }

}