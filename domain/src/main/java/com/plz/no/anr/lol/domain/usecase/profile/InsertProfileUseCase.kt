package com.plz.no.anr.lol.domain.usecase.profile

import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.domain.repository.ProfileRepository
import com.plz.no.anr.lol.domain.usecase.base.BaseUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow

class InsertProfileUseCase(
    coroutineDispatcher: CoroutineDispatcher,
    private val appRepository: ProfileRepository
): BaseUseCase<Profile, Unit>(coroutineDispatcher) {

    override fun execute(parameter: Profile): Flow<Result<Unit>> {
        return appRepository.insertProfile(parameter)
    }

}