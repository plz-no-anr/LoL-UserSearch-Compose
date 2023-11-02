package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Result<Profile?>>

    fun insertProfile(profile: Profile): Flow<Result<Unit>>

    fun deleteProfile(): Flow<Result<Unit>>

}
