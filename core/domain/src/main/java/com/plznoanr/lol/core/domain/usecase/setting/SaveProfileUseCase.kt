package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.model.Profile
import com.plznoanr.lol.core.data.repository.ProfileRepository
import javax.inject.Inject

class SaveProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {

    suspend operator fun invoke(profile: Profile) {
        return profileRepository.upsertProfile(profile)
    }

}