package com.plznoanr.domain.usecase.summoner

import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class RequestSummonerUseCase(
    private val appRepository: AppRepository
): BaseUseCase<String, Summoner>() {

    override fun execute(parameter: String): Flow<Result<Summoner>> {
        return appRepository.requestSummoner(parameter)
    }

}