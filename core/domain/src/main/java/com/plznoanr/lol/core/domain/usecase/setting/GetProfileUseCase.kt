package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {

    operator fun invoke(): Flow<Profile?> {
        return profileRepository.getProfile()
    }

}