package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.data.repository.AppRepository
import javax.inject.Inject

class SaveKeyUseCase @Inject constructor(
    private val appRepository: AppRepository,
) {
    suspend operator fun invoke(key: String) {
        appRepository.insertApiKey(key)
    }

}