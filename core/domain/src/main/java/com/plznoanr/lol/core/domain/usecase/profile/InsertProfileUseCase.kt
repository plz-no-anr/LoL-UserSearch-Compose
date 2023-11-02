package com.plznoanr.lol.core.domain.usecase.profile

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.domain.usecase.base.BaseUseCase
import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.data.repository.ProfileRepository
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