package com.plznoanr.domain.usecase.summoner

import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class DeleteAllSummonerUseCase(
    private val appRepository: AppRepository
): BaseUseCase<Unit, Unit>() {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return appRepository.deleteSummonerAll()
    }

}