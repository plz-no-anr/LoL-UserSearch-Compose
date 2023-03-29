package com.plznoanr.domain.usecase.profile

import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertProfileUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: AppRepository
): BaseUseCase<Profile, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Profile): Flow<Result<Unit>> {
        return appRepository.insertProfile(parameter)
    }

}