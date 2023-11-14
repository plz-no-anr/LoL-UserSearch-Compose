package com.plznoanr.lol.core.data.repository

import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.common.model.AppError
import com.plznoanr.lol.core.data.utils.JsonParser
import com.plznoanr.lol.core.data.utils.asEntity
import com.plznoanr.lol.core.database.data.app.AppLocalDataSource
import com.plznoanr.lol.core.datastore.SettingPreferenceDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appLocalDataSource: AppLocalDataSource,
    private val settingPreferenceDataSource: SettingPreferenceDataSource,
    private val jsonParser: JsonParser,
    @AppDispatchers.Default private val defaultDispatcher: CoroutineDispatcher
) : AppRepository {

    override fun getApiKey(): Flow<String?> = settingPreferenceDataSource.apiKeyFlow

    override suspend fun insertApiKey(key: String) {
        settingPreferenceDataSource.updateApiKey(key)
    }

    override suspend fun deleteApiKey() {
        settingPreferenceDataSource.clearApiKey()
    }

    private suspend fun isLocalInitialize() = settingPreferenceDataSource.initFlow.first() ?: false

    private suspend fun getJson() = requireNotNull(jsonParser.getLocalJson()) {
        throw Exception(AppError.NoJsonData.parse())
    }

    override fun initializeJsonData(): Flow<Result<Boolean>> = flow {
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
                settingPreferenceDataSource.updateInit(true)
                emit(Result.success(true))
            } else {
                emit(Result.success(false))
            }
        }
    }.catch { e ->
        Timber.w("catchResultError : $e")
        emit(
            Result.failure(e)
        )
    }.flowOn(defaultDispatcher)

}