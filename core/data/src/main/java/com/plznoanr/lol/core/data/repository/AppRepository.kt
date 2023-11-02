package com.plznoanr.lol.core.data.repository

import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getApiKey(): Flow<Result<String?>>

    fun insertApiKey(key: String): Flow<Result<Unit>>

    fun deleteApiKey(): Flow<Result<Unit>>

    fun initLocalJson(): Flow<Result<Boolean>>

}