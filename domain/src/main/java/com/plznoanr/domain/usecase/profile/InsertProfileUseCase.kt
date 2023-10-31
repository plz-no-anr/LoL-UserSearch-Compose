package com.plznoanr.domain.usecase.profile

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.domain.usecase.base.BaseUseCase
import com.plznoanr.model.Profile
import com.plznoanr.data.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<Profile, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Profile): Flow<Result<Unit>> {
        return profileRepository.insertProfile(parameter)
    }

}