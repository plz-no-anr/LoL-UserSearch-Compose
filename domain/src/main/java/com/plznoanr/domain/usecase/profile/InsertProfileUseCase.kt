package com.plznoanr.domain.usecase.profile

import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.repository.AppRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.flow.Flow

class InsertProfileUseCase(
    private val appRepository: AppRepository
): BaseUseCase<Profile, Unit>() {

    override fun execute(parameter: Profile): Flow<Result<Unit>> {
        return appRepository.insertProfile(parameter)
    }

}