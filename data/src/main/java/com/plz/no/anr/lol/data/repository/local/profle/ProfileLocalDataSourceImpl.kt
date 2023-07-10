package com.plz.no.anr.lol.data.repository.local.profle

import com.plz.no.anr.lol.data.db.dao.LolDao
import com.plz.no.anr.lol.data.model.local.ProfileEntity

class ProfileLocalDataSourceImpl(
    private val dao: LolDao
) : ProfileLocalDataSource {

    override suspend fun getProfile(): ProfileEntity = dao.getProfile()

    override suspend fun insertProfile(profileEntity: ProfileEntity) {
        dao.insertProfile(profileEntity)
    }

    override suspend fun updateProfile(profileEntity: ProfileEntity) {
        dao.updateProfile(profileEntity)
    }

    override suspend fun deleteProfile() {
        dao.deleteProfile()
    }

}