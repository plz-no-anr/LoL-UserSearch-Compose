package com.plz.no.anr.lol.domain.repository

import com.plz.no.anr.lol.domain.model.Profile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {

    fun getProfile(): Flow<Result<Profile?>>

    fun insertProfile(profile: Profile): Flow<Result<Unit>>

    fun deleteProfile(): Flow<Result<Unit>>

}