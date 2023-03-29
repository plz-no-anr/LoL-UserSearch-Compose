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
import com.plznoanr.data.utils.*
import com.plznoanr.domain.model.*
import com.plznoanr.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.time.LocalDate

class AppRepositoryImpl(
    @CoroutineQualifiers.IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val jsonUtils: JsonUtils,
) : AppRepository {
    override var apiKey: String?
        get() = preferenceDataSource.apiKey
        set(value) {
            preferenceDataSource.apiKey = value
        }

    override var isInit: Boolean
        get() = preferenceDataSource.isInit
        set(value) {
            preferenceDataSource.isInit = value
        }
    override fun initLocalJson(): Flow<Result<Boolean>> = flow {
        if (!isInit) {
            val json = requireNotNull(jsonUtils.getLocalJson()) {
                emit(Result.failure(Exception("Local Json is null")))
                return@flow
            }
            json.map.data.values.forEach {
                localDataSource.insertMap(it.toEntity())
            }
            json.champ.data.values.forEach {
                localDataSource.insertChamp(it.toEntity())
            }
            json.rune.forEach {
                localDataSource.insertRune(it.toEntity())
            }
            json.summoner.data.values.forEach {
                localDataSource.insertSpell(it.toEntity())
            }
            isInit = true
            emit(Result.success(true))
        } else {
            emit(Result.success(false))
        }
    }

    override fun getSearchList(): Flow<Result<List<Search>>> = flow {
        emit(makeResult(coroutineDispatcher) {
            localDataSource.getSearch().map { it.toDomain() }
        })
    }

    override fun insertSearch(search: Search): Flow<Result<Unit>> = flow {
        localDataSource.insertSearch(search.toEntity())
        emit(Result.success(Unit))
    }


    override fun deleteSearch(sName: String): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearch(sName)
        emit(Result.success(Unit))
    }

    override fun deleteSearchAll(): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearchAll()
        emit(Result.success(Unit))
    }

    override fun getSummonerList(): Flow<Result<List<Summoner>>> = flow {
        emit(makeResult(coroutineDispatcher) {
            localDataSource.getSummoner().map { it.toDomain() }
        })
    }

    override fun insertSummoner(summoner: Summoner): Flow<Result<Unit>> = flow {
        localDataSource.insertSummoner(summoner.toEntity())
        emit(Result.success(Unit))
    }

    override fun deleteSummoner(name: String): Flow<Result<Unit>> = flow {
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

            val icon = summoner.profileIconId.toIcon()

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
    }

    override fun requestSpectator(name: String): Flow<Result<Spectator>> = flow {
        try {
            val key = requireNotNull(apiKey) {
                emit(Result.failure(Exception("FORBIDDEN")))
                return@flow
            }

            val summoner = remoteDataSource.requestSummoner(name, key)

            val spectator = remoteDataSource.requestSpectator(summoner.id, key)

            val spectatorResult = spectator?.let { response ->
                Spectator(
                    map = response.mapId.toMap(),
                    banChamp = response.bannedChampions.toBanChamp(),
                    redTeam = response.participants.filter { it.teamId.toTeam() == Team.RED }.toDomain(),
                    blueTeam = response.participants.filter { it.teamId.toTeam() == Team.BLUE }.toDomain()
                )
            }

            spectatorResult?.let {
                emit(Result.success(it))
            } ?: emit(Result.failure(Exception("Spectator is null".parseError(1000))))

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private suspend fun <T> makeResult(
        dispatcher: CoroutineDispatcher,
        call: suspend () -> T
    ): Result<T> = runCatching {
        withContext(dispatcher) {
            call()
        }
    }

    private suspend fun List<SpectatorResponse.BannedChampion>.toBanChamp() = map {
        Spectator.BanChamp(
            team = it.teamId.toTeam(),
            champName = it.championId.toChampInfo().first
        )
    }

    private suspend fun List<SpectatorResponse.CurrentGameParticipant>.toDomain() = map {
        val champInfo = it.championId.toChampInfo()
        Spectator.SpectatorInfo(
            name = it.summonerName,
            champName = champInfo.first,
            champImg = champInfo.second,
            team = it.teamId.toTeam(),
            spell1 = it.spell1Id.toSpellImage(),
            spell2 = it.spell2Id.toSpellImage(),
            runeStyle = it.perks.perkStyle.toRuneStyle(),
            subStyle = it.perks.perkSubStyle.toRuneStyle(),
            mainRune = getMainRune(it.perks.perkStyle, it.perks.perkIds[0]),
            rune = getRunes(it.perks.perkStyle, it.perks.perkSubStyle, it.perks.perkIds),
        )
    }
    private fun Long.toTeam() = if (this.toString() == "100") Team.BLUE else Team.RED
    private suspend fun Long.toMap(): String = withContext(coroutineDispatcher) {
            localDataSource.getMaps().forEach {
                if (it.mapId == this@toMap.toString()) {
                    return@withContext it.mapName
                }
            }
            return@withContext "Not Found"
        }

    private suspend fun Long.toChampInfo(): Pair<String, String> = withContext(coroutineDispatcher) {
        localDataSource.getChamps().forEach {
            if (this@toChampInfo == (-1).toLong()) return@withContext "NoBan" to "NoBan"
            if (it.key == this@toChampInfo.toString()) {
                return@withContext it.name to it.image.full.toChampImage()
            }
        }
        return@withContext "Not Found" to "Not Found"
    }
    private suspend fun Long.toRuneStyle(): Spectator.SpectatorInfo.Rune = withContext(coroutineDispatcher) {
        localDataSource.getRunes().forEach {
            if (it.id == this@toRuneStyle) {
                return@withContext Spectator.SpectatorInfo.Rune(it.name, it.icon)
            }
        }
        return@withContext Spectator.SpectatorInfo.Rune("Not Found", "Not Found")
    }
    private suspend fun Long.toSpellImage(): String = withContext(coroutineDispatcher) {
        localDataSource.getSpells().forEach {
            if (it.key == this@toSpellImage.toString()) {
                return@withContext it.image.full.toSpellImage()
            }
        }
        return@withContext "Not Found"
    }

    private suspend fun getMainRune(perkStyle: Long, perks: Long): String = withContext(coroutineDispatcher) {
        localDataSource.getRunes().forEach {
            if (it.id == perkStyle) {
              it.slots.forEach { slot ->
                  slot.runes.forEach { rune ->
                      if (rune.id == perks) {
                          return@withContext rune.icon
                      }

                  }
              }
            }
        }
        return@withContext "Not Found"
    }

    private suspend fun getRunes(
        perkStyle: Long,
        subStyle: Long,
        perks: List<Long>
    ): List<Spectator.SpectatorInfo.Rune> = withContext(coroutineDispatcher) {
        val runeNames = MutableList(6) { Spectator.SpectatorInfo.Rune("", "") }

        localDataSource.getRunes().forEach {
            if (it.id == perkStyle) {
                it.slots.forEach { slot ->
                    slot.runes.forEach { rune ->
                        if (rune.id == perks[0]) {
                            runeNames[0] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[1]) {
                            Timber.d("Rune2:${rune.icon}")
                            runeNames[1] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[2]) {
                            Timber.d("Rune3:${rune.icon}")
                            runeNames[2] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[3]) {
                            Timber.d("Rune4:${rune.icon}")
                            runeNames[3] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                    }
                }
            } else if (it.id == subStyle) {
                it.slots.forEach { slot ->
                    slot.runes.forEach { rune ->
                        if (rune.id == perks[4]) {
                            Timber.d("Rune5:${rune.icon}")
                            runeNames[4] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[5]) {
                            Timber.d("Rune6:${rune.icon}")
                            runeNames[5] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                    }
                }
            }
        }
        return@withContext runeNames
    }

}