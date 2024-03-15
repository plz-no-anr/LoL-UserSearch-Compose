package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.di.AppDispatcher
import com.plznoanr.lol.core.common.di.Dispatcher
import com.plznoanr.lol.core.common.model.getOrNull
import com.plznoanr.lol.core.data.repository.SummonerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RefreshSummonerListUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke() {
        withContext(ioDispatcher) {
            val list = summonerRepository.getSummonerAll().first()
            list.launchMap(this) {
                val response = summonerRepository.requestSummoner(it.nickname).getOrNull() ?: it
                summonerRepository.upsertSummoner(response)
            }
        }
    }

    private fun <T> List<T>.launchMap(
        coroutineScope: CoroutineScope,
        block: suspend (T) -> Unit
    ) {
        map {
            coroutineScope.launch { // context switch overhead?
                Timber.d("this Thread: ${Thread.currentThread().name}")
                block(it)
            }
        }
    }

}