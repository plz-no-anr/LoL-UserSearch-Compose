package com.plz.no.anr.lol.data.repository

import com.plz.no.anr.lol.data.model.common.AppError
import com.plz.no.anr.lol.data.repository.local.DataStoreManager
import com.plz.no.anr.lol.data.repository.local.PreferenceDataSource
import com.plz.no.anr.lol.data.repository.local.app.AppLocalDataSource
import com.plz.no.anr.lol.data.utils.JsonUtils
import com.plz.no.anr.lol.data.utils.toEntity
import com.plz.no.anr.lol.domain.repository.AppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import timber.log.Timber

internal class AppRepositoryImpl(
    private val appLocalDataSource: AppLocalDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val dataStoreManager: DataStoreManager,
    private val jsonUtils: JsonUtils,
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

    private suspend fun getLocalInit() = dataStoreManager.initFlow.first() ?: false

    private suspend fun getJson() = requireNotNull(jsonUtils.getLocalJson()) {
        throw Exception(AppError.NoJsonData.parse())
    }

    override fun initLocalJson(): Flow<Result<Boolean>> = flow {
        Timber.d("isInit: ${getLocalInit()}")
        if (!getLocalInit()) {
            val json = getJson()

            json.map.data.values.forEach {
                appLocalDataSource.insertMap(it.toEntity())
            }
            json.champ.data.values.forEach {
                appLocalDataSource.insertChamp(it.toEntity())
            }
            json.rune.forEach {
                appLocalDataSource.insertRune(it.toEntity())
            }
            json.summoner.data.values.forEach {
                appLocalDataSource.insertSpell(it.toEntity())
            }
            dataStoreManager.storeInit(true)
            emit(Result.success(true))
        } else {
            emit(Result.success(false))
        }
    }


}