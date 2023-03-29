package com.plznoanr.domain.usecase.profile

import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: AppRepository
): BaseUseCase<Unit, Profile?>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Profile?>> {
        return appRepository.getProfile()
    }

}