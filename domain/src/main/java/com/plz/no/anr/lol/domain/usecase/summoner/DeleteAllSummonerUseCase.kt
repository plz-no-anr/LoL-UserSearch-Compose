package com.plz.no.anr.lol.domain.usecase.summoner

import com.plz.no.anr.lol.domain.repository.SummonerRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class DeleteAllSummonerUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: SummonerRepository
): BaseUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return appRepository.deleteSummonerAll()
    }

}