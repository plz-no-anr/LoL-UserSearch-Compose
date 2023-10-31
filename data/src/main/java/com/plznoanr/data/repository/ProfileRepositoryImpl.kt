package com.plznoanr.data.repository

import com.plznoanr.data.model.local.asDomain
import com.plznoanr.data.repository.local.profle.ProfileLocalDataSource
import com.plznoanr.data.utils.asEntity
import com.plznoanr.model.Profile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
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