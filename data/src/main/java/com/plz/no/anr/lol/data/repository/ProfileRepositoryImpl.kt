package com.plz.no.anr.lol.data.repository

import com.plz.no.anr.lol.data.model.local.toDomain
import com.plz.no.anr.lol.data.repository.local.profle.ProfileLocalDataSource
import com.plz.no.anr.lol.data.utils.toEntity
import com.plz.no.anr.lol.domain.model.Profile
import com.plz.no.anr.lol.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ProfileRepositoryImpl (
    private val localDataSource: ProfileLocalDataSource
) : ProfileRepository {

    override fun getProfile(): Flow<Result<Profile?>> = flow {
        localDataSource.getProfile()?.also {
            emit(Result.success(it.toDomain()))
        } ?: emit(Result.success(null))
    }

    override fun insertProfile(profile: Profile): Flow<Result<Unit>> = flow {
        localDataSource.insertProfile(profile.toEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteProfile(): Flow<Result<Unit>> = flow {
        localDataSource.deleteProfile().run {
            emit(Result.success(Unit))
        }
    }

}