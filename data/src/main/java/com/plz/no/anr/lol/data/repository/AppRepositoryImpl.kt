package com.plz.no.anr.lol.data.repository

import com.plz.no.anr.lol.data.model.common.AppError
import com.plz.no.anr.lol.data.repository.local.DataStoreManager
import com.plz.no.anr.lol.data.repository.local.app.AppLocalDataSource
import com.plz.no.anr.lol.data.utils.JsonUtils
import com.plz.no.anr.lol.data.utils.asEntity
import com.plz.no.anr.lol.data.utils.catchResultError
import com.plz.no.anr.lol.domain.repository.AppRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber

internal class AppRepositoryImpl(
    private val appLocalDataSource: AppLocalDataSource,
    private val dataStoreManager: DataStoreManager,
    private val jsonUtils: JsonUtils
) : AppRepository {

    override fun getApiKey(): Flow<Result<String?>> = flow {
        val apiKey = dataStoreManager.apiKeyFlow.first()
        emit(Result.success(apiKey))
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

    private suspend fun getJson() = requireNotNull(jsonUtils.getLocalJson()) {
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