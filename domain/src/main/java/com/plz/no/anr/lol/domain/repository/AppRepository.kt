package com.plz.no.anr.lol.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getApiKey(): Flow<Result<String?>>

    fun insertApiKey(key: String): Flow<Result<Unit>>

    fun deleteApiKey(): Flow<Result<Unit>>

    fun initLocalJson(): Flow<Result<Boolean>>

}