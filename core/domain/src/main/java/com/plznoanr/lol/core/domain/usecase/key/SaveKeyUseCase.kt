package com.plznoanr.lol.core.domain.usecase.key

import com.plznoanr.lol.core.data.repository.AppRepository
import javax.inject.Inject

class SaveKeyUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    suspend fun invoke(key: String) {
        return appRepository.insertApiKey(key)
    }

}