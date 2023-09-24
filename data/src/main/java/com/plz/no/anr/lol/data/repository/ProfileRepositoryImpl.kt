package com.plz.no.anr.lol.data.repository

import com.plz.no.anr.lol.data.model.local.asDomain
import com.plz.no.anr.lol.data.repository.local.profle.ProfileLocalDataSource
import com.plz.no.anr.lol.data.utils.asEntity
import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ProfileRepositoryImpl (
    private val localDataSource: ProfileLocalDataSource
) : ProfileRepository {

    override fun getProfile(): Flow<Result<Profile?>> =
        localDataSource.getProfile().map {
            Result.success(it?.asDomain())
        }

    override fun insertProfile(profile: Profile): Flow<Result<Unit>> = flow {
        localDataSource.insertProfile(profile.asEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteProfile(): Flow<Result<Unit>> = flow {
        localDataSource.deleteProfile().run {
            emit(Result.success(Unit))
        }
    }

}