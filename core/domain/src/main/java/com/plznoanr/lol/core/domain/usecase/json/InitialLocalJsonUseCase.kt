package com.plznoanr.lol.core.domain.usecase.json

import com.plznoanr.lol.core.common.model.Result
import com.plznoanr.lol.core.data.repository.AppRepository
import javax.inject.Inject

/**
 * 초기 inital시 true
 * else
 * false
 */
class InitialLocalJsonUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return appRepository.initializeJsonData()
    }

}