package com.plznoanr.domain.usecase.profile

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.model.Profile
import com.plznoanr.data.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class GetProfileUseCase(
    private val profileRepository: ProfileRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<Unit, Profile?>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Profile?>> {
        return profileRepository.getProfile()
    }

}