package com.plznoanr.lol.core.database.data.profle

import com.plznoanr.lol.core.database.dao.LolDao
import com.plznoanr.lol.core.database.model.ProfileEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProfileLocalDataSourceImpl @Inject constructor(
    private val dao: LolDao
) : ProfileLocalDataSource {

    override fun getProfile(): Flow<ProfileEntity?> = dao.getProfile()

    override suspend fun upsertProfile(profileEntity: ProfileEntity) {
        dao.upsertProfile(profileEntity)
    }

    override suspend fun deleteProfile() {
        dao.deleteProfile()
    }

}