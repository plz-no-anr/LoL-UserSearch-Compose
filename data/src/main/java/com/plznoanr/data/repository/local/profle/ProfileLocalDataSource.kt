package com.plznoanr.data.repository.local.profle

import com.plznoanr.data.model.local.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileLocalDataSource {

    fun getProfile() : Flow<ProfileEntity?>

    suspend fun insertProfile(profileEntity: ProfileEntity)

    suspend fun updateProfile(profileEntity: ProfileEntity)

    suspend fun deleteProfile()

}