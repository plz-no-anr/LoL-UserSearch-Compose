package com.plznoanr.lol.core.domain.usecase.profile

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.data.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<Unit, Profile?>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Profile?>> {
        return profileRepository.getProfile()
    }

}