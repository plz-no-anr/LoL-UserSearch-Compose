package com.plz.no.anr.lol.data.repository.local.profle

import com.plz.no.anr.lol.data.model.local.ProfileEntity

interface ProfileLocalDataSource {

    suspend fun getProfile() : ProfileEntity?

    suspend fun insertProfile(profileEntity: ProfileEntity)

    suspend fun updateProfile(profileEntity: ProfileEntity)

    suspend fun deleteProfile()

}