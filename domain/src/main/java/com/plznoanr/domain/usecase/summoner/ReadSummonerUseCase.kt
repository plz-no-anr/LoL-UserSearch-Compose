package com.plznoanr.domain.usecase.summoner

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.SummonerRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.domain.usecase.search.SaveSearchUseCase
import com.plznoanr.model.Search
import com.plznoanr.model.Summoner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class ReadSummonerUseCase @Inject constructor(
    private val requestSummonerUseCase: RequestSummonerUseCase,
    private val getSummonerUseCase: GetSummonerUseCase,
    private val saveSearchUseCase: SaveSearchUseCase,
    private val saveSummonerUseCase: SaveSummonerUseCase,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<String, Summoner>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Summoner>> =
        requestSummonerUseCase(parameter)
            .onEach {
                saveSearchUseCase(
                    Search(
                        name = it.getOrThrow().name,
                        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                ).first()
                saveSummonerUseCase(it.getOrThrow()).first()
            }.map {
                if (it.isSuccess) {
                    it
                } else {
                    getSummonerUseCase(parameter).first()
                }
            }

}