package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.data.utils.asEntity
import com.plznoanr.lol.core.database.data.profle.ProfileLocalDataSource
import com.plznoanr.lol.core.database.model.asDomain
import com.plznoanr.lol.core.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ProfileRepository {

    fun getProfile(): Flow<Profile?>

    suspend fun upsertProfile(profile: Profile)

    suspend fun deleteProfile()

}

class DefaultProfileRepository @Inject constructor(
    private val localDataSource: ProfileLocalDataSource
) : ProfileRepository {

    override fun getProfile(): Flow<Profile?> =
        localDataSource.getProfile().map {
            it?.asDomain()
        }

    override suspend fun upsertProfile(profile: Profile) {
        localDataSource.upsertProfile(profile.asEntity())
    }

    override suspend fun deleteProfile() {
        localDataSource.deleteProfile()
    }

}