package com.plznoanr.data.repository

import com.plznoanr.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Result<Profile?>>

    fun insertProfile(profile: Profile): Flow<Result<Unit>>

    fun deleteProfile(): Flow<Result<Unit>>

}