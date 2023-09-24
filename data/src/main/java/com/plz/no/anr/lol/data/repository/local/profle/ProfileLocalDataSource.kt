package com.plz.no.anr.lol.data.repository.local.profle

import com.plz.no.anr.lol.data.model.local.ProfileEntity
import kotlinx.coroutines.flow.Flow

interface ProfileLocalDataSource {

    fun getProfile() : Flow<ProfileEntity?>

    suspend fun insertProfile(profileEntity: ProfileEntity)

    suspend fun updateProfile(profileEntity: ProfileEntity)

    suspend fun deleteProfile()

}