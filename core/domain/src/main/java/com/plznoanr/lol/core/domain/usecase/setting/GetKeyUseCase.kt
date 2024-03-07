package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.data.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetKeyUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<String> {
        return repository.getApiKey()
    }

}