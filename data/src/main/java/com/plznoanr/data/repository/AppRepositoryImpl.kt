package com.plznoanr.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.plznoanr.data.di.CoroutineQualifiers
import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.remote.*
import com.plznoanr.data.repository.local.LocalDataSource
import com.plznoanr.data.repository.local.PreferenceDataSource
import com.plznoanr.data.repository.remote.RemoteDataSource
import com.plznoanr.data.utils.QueueType
import com.plznoanr.data.utils.getSummonerIcon
import com.plznoanr.data.utils.parseError
import com.plznoanr.data.utils.toEntity
import com.plznoanr.domain.model.Profile
import com.plznoanr.domain.model.Search
import com.plznoanr.domain.model.Summoner
import com.plznoanr.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import java.time.LocalDate

class AppRepositoryImpl(
    @CoroutineQualifiers.IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val preferenceDataSource: PreferenceDataSource
) : AppRepository {
    override var apiKey: String?
        get() = preferenceDataSource.apiKey
        set(value) {
            preferenceDataSource.apiKey = value
        }

    override fun getSearchList(): Flow<Result<List<Search>>> = flow {
        emit(makeResult(coroutineDispatcher) {
            localDataSource.getSearch().map { it.toDomain() }
        })
    }.flowOn(coroutineDispatcher)

    override fun insertSearch(search: Search): Flow<Result<Unit>> = flow {
        localDataSource.insertSearch(search.toEntity())
        emit(Result.success(Unit))
    }.flowOn(coroutineDispatcher)


    override fun deleteSearch(sName: String): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearch(sName)
        emit(Result.success(Unit))
    }.flowOn(coroutineDispatcher)

    override fun deleteSearchAll(): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearchAll()
        emit(Result.success(Unit))
    }.flowOn(coroutineDispatcher)

    override fun getSummonerList(): Flow<Result<List<Summoner>>> = flow {
        emit(makeResult(coroutineDispatcher) {
            localDataSource.getSummoner().map { it.toDomain() }
        })
    }.flowOn(coroutineDispatcher)

    override fun insertSummoner(summoner: Summoner): Flow<Result<Unit>> = flow {
        localDataSource.insertSummoner(summoner.toEntity())
        emit(Result.success(Unit))
    }.flowOn(coroutineDispatcher)

    override fun deleteSummoner(name: String): Flow<Result<Unit>> = flow{
        localDataSource.deleteSummoner(name)
        emit(Result.success(Unit))
    }

    override fun deleteSummonerAll(): Flow<Result<Unit>> = flow {
        localDataSource.deleteSummonerAll()
        emit(Result.success(Unit))
    }

    override fun getProfile(): Flow<Result<Profile?>> = flow {
        val entity: ProfileEntity? = localDataSource.getProfile()
        entity?.let {
            emit(Result.success(entity.toDomain()))
        } ?: emit(Result.success(null))
    }

    override fun insertProfile(profile: Profile): Flow<Result<Unit>> = flow {
        localDataSource.insertProfile(profile.toEntity())
        emit(Result.success(Unit))
    }

    override fun deleteProfile(): Flow<Result<Unit>> = flow {
        localDataSource.deleteProfile()
        emit(Result.success(Unit))
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun requestSummoner(name: String): Flow<Result<Summoner>> = flow {
        try {
            val key = requireNotNull(apiKey) {
                emit(Result.failure(Exception("FORBIDDEN")))
                return@flow
            }

            val summoner = remoteDataSource.requestSummoner(name, key)

            val league = remoteDataSource.requestLeague(summoner.id, key)

            val spectator = remoteDataSource.requestSpectator(summoner.id, key)

            var summonerResult: Summoner? = null

            val icon = getSummonerIcon(summoner.profileIconId)

            league.forEach {
                if (it.queueType == QueueType.SOLO_RANK) {
                    summonerResult = Summoner(
                        name = summoner.name,
                        level = summoner.summonerLevel.toString(),
                        icon = icon,
                        tier = it.tier,
                        leaguePoints = it.leaguePoints,
                        rank = it.rank,
                        wins = it.wins,
                        losses = it.losses,
                        miniSeries = it.miniSeries?.toDomain(),
                        isPlaying = spectator != null && spectator.gameId != 0L
                    )
                }
            }

            summonerResult?.let {
                localDataSource.insertSearch(
                    SearchEntity(
                        name = summoner.name,
                        date = LocalDate.now().toString()
                    )
                )
                localDataSource.insertSummoner(it.toEntity())

                emit(Result.success(it))
            } ?: emit(Result.failure(Exception("Summoner is null".parseError(404))))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(coroutineDispatcher)

    private suspend fun <T> makeResult(
        dispatcher: CoroutineDispatcher,
        call: suspend () -> T
    ): Result<T> = runCatching {
        withContext(dispatcher) {
            call()
        }
    }
}