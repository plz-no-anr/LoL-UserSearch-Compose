package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteAllSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher,
): BaseUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return summonerRepository.deleteSummonerAll()
    }

}