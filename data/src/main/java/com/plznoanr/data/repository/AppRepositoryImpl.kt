package com.plznoanr.data.repository

import com.plznoanr.data.repository.local.DataStoreManager
import com.plznoanr.data.repository.local.app.AppLocalDataSource
import com.plznoanr.data.utils.JsonParser
import com.plznoanr.data.utils.asEntity
import com.plznoanr.data.utils.catchResultError
import com.plznoanr.model.common.AppError
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appLocalDataSource: AppLocalDataSource,
    private val dataStoreManager: DataStoreManager,
    private val jsonParser: JsonParser
) : AppRepository {

    override fun getApiKey(): Flow<Result<String?>> = dataStoreManager.apiKeyFlow.map {
        Result.success(it)
    }

    override fun insertApiKey(key: String): Flow<Result<Unit>> = flow {
        dataStoreManager.storeApiKey(key)
        emit(Result.success(Unit))
    }

    override fun deleteApiKey(): Flow<Result<Unit>> = flow {
        dataStoreManager.clearApiKey()
        emit(Result.success(Unit))
    }

    private suspend fun isLocalInitialize() = dataStoreManager.initFlow.first() ?: false

    private suspend fun getJson() = requireNotNull(jsonParser.getLocalJson()) {
        throw Exception(AppError.NoJsonData.parse())
    }

    override fun initLocalJson(): Flow<Result<Boolean>> = flow {
        val init = isLocalInitialize()
        Timber.d("isInit: $init")
        coroutineScope {
            if (!init) {
                val json = getJson()
                val mapJob = launch {
                    json.map.data.values.forEach {
                        appLocalDataSource.insertMap(it.asEntity())
                    }
                }
                val champJob = launch {
                    json.champ.data.values.forEach {
                        appLocalDataSource.insertChamp(it.asEntity())
                    }
                }
                val runeJob = launch {
                    json.rune.forEach {
                        appLocalDataSource.insertRune(it.asEntity())
                    }
                }
                val summonerJob = launch {
                    json.summoner.data.values.forEach {
                        appLocalDataSource.insertSpell(it.asEntity())
                    }
                }
                mapJob.join()
                champJob.join()
                runeJob.join()
                summonerJob.join()
                dataStoreManager.storeInit(true)
                emit(Result.success(true))
            } else {
                emit(Result.success(false))
            }
        }
    }.catchResultError()


}