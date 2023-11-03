package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.domain.usecase.search.SaveUseCase
import com.plznoanr.lol.core.model.Search
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import timber.log.Timber
import javax.inject.Inject

class ReadSummonerUseCase @Inject constructor(
    private val requestSummonerUseCase: RequestSummonerUseCase,
    private val getSummonerUseCase: GetSummonerUseCase,
    private val saveSearchUseCase: SaveUseCase,
    private val saveSummonerUseCase: SaveSummonerUseCase,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
) : BaseUseCase<String, Summoner>(coroutineDispatcher) {

    override fun execute(parameter: String): Flow<Result<Summoner>> =
        requestSummonerUseCase(parameter)
            .onEach {
                Timber.d("현재 시간${Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())}")
                saveSearchUseCase(
                    Search(
                        name = it.getOrThrow().name,
                        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    )
                )
                saveSummonerUseCase(it.getOrThrow()).first()
            }.map {
                if (it.isSuccess) {
                    it
                } else {
                    getSummonerUseCase(parameter).first()
                }
            }

}