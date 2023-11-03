package com.plznoanr.lol.core.database.data.profle

import com.plznoanr.lol.core.database.model.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileLocalDataSource {

    fun getProfile() : Flow<ProfileEntity?>

    suspend fun insertProfile(profileEntity: ProfileEntity)

    suspend fun updateProfile(profileEntity: ProfileEntity)

    suspend fun deleteProfile()

}