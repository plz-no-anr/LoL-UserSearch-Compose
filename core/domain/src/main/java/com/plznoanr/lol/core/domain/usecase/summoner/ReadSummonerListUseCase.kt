package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReadSummonerListUseCase @Inject constructor(
    private val getSummonerListUseCase: GetSummonerListUseCase,
    private val readSummonerUseCase: ReadSummonerUseCase,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher,
): BaseUseCase<Paging, List<Summoner>>(coroutineDispatcher) {

    override fun execute(parameter: Paging): Flow<Result<List<Summoner>>> =
        getSummonerListUseCase(parameter)
            .map { result ->
                val summonerList = result.getOrThrow()
                val list = summonerList.map { summoner ->
                    readSummonerUseCase(summoner.name).first().getOrThrow()
                }
                Result.success(list)
            }
}