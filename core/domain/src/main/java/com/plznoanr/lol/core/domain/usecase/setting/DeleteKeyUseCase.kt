package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.data.repository.AppRepository
import javax.inject.Inject

class DeleteKeyUseCase @Inject constructor(
    private val repository: AppRepository,
) {
    suspend operator fun invoke() {
        return repository.deleteApiKey()
    }

}