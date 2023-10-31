package com.plznoanr.domain.usecase.profile

import com.plznoanr.data.di.AppDispatchers
import com.plznoanr.data.repository.ProfileRepository
import com.plznoanr.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository,
    @AppDispatchers.IO coroutineDispatcher: CoroutineDispatcher
): BaseUseCase<Unit, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Unit): Flow<Result<Unit>> {
        return profileRepository.deleteProfile()
    }

}