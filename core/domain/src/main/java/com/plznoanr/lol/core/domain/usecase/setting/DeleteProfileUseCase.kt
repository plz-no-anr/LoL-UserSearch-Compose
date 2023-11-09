package com.plznoanr.lol.core.domain.usecase.setting

import com.plznoanr.lol.core.data.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
) {

    suspend operator fun invoke() {
        return profileRepository.deleteProfile()
    }

}