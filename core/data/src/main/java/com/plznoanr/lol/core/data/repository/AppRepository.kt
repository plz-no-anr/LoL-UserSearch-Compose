package com.plznoanr.lol.core.data.repository

import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getApiKey(): Flow<String?>

    suspend fun insertApiKey(key: String)

    suspend fun deleteApiKey()

    fun initializeJsonData(): Flow<Result<Boolean>>

}