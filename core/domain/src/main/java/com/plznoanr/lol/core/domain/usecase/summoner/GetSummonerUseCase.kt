package com.plznoanr.lol.core.domain.usecase.summoner

import com.plznoanr.lol.core.common.model.Result
import com.plznoanr.lol.core.data.repository.SummonerRepository
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Summoner
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetSummonerUseCase @Inject constructor(
    private val summonerRepository: SummonerRepository
) {

    suspend operator fun invoke(nickname: Nickname): Result<Summoner> {
        return when (val result = summonerRepository.requestSummoner(nickname)) {
            is Result.Success -> {
                val remote = result.data
                summonerRepository.upsertSummoner(remote)
                val local = summonerRepository.getSummoner(remote.id).first()
                return if (local != null) {
                    Result.Success(local)
                } else {
                    Result.SummonerNullError
                }
            }
            is Result.Error -> result
        }
    }

}