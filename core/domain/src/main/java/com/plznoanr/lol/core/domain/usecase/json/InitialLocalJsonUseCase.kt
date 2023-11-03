package com.plznoanr.lol.core.domain.usecase.json

import com.plznoanr.lol.core.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 초기 inital시 true
 * else
 * false
 */
class InitialLocalJsonUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<Result<Boolean>> {
        return appRepository.initializeJsonData()
    }

}