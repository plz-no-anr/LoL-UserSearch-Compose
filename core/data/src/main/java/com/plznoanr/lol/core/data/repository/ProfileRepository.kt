package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Profile?>

    suspend fun upsertProfile(profile: Profile)

    suspend fun deleteProfile()

}
