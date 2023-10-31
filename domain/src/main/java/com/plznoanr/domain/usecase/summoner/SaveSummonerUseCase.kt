package com.plznoanr.domain.usecase.summoner

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.SummonerRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.model.Summoner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SaveSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher,
): BaseUseCase<Summoner, Unit>(coroutineDispatcher) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun execute(parameter: Summoner): Flow<Result<Unit>> =
        summonerRepository.getSummonerList()
            .map { result ->
                result.getOrNull()?.let { summoners ->
                    summoners.find { parameter.name == it.name }
                }
            }.flatMapConcat {
                if (it != null) {
                    summonerRepository.updateSummoner(parameter)
                } else {
                    summonerRepository.insertSummoner(parameter)
                }
            }

}