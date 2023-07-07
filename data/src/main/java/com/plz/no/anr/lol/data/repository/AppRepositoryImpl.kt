package com.plz.no.anr.lol.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.plz.no.anr.lol.data.di.CoroutineQualifiers
import com.plz.no.anr.lol.data.model.common.AppError
import com.plz.no.anr.lol.data.model.local.SearchEntity
import com.plz.no.anr.lol.data.model.local.toDomain
import com.plz.no.anr.lol.data.model.remote.*
import com.plz.no.anr.lol.data.repository.local.LocalDataSource
import com.plz.no.anr.lol.data.repository.local.PreferenceDataSource
import com.plz.no.anr.lol.data.repository.remote.RemoteDataSource
import com.plz.no.anr.lol.data.utils.*
import com.plz.no.anr.lol.domain.model.*
import com.plz.no.anr.lol.domain.repository.AppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.LocalDate

internal class AppRepositoryImpl(
    @CoroutineQualifiers.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val preferenceDataSource: PreferenceDataSource,
    private val jsonUtils: JsonUtils,
) : AppRepository {
    private val defaultText = "Not Found"

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

    private fun getAuthKey() = requireNotNull(apiKey) {
        throw Exception(AppError.Forbidden.parse())
    }

    private suspend fun getJson() = requireNotNull(jsonUtils.getLocalJson()) {
        throw Exception(AppError.NoJsonData.parse())
    }

    override fun initLocalJson(): Flow<Result<Boolean>> = flow {
        if (!isInit) {
            val json = getJson()

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
        emit(Result.success(localDataSource.getSearch().toSearchList()))
    }

    override fun insertSearch(search: Search): Flow<Result<Unit>> = flow {
        localDataSource.insertSearch(search.toEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSearch(sName: String): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearch(sName).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSearchAll(): Flow<Result<Unit>> = flow {
        localDataSource.deleteSearchAll().run {
            emit(Result.success(Unit))
        }
    }

    override fun insertSummoner(summoner: Summoner): Flow<Result<Unit>> = flow {
        localDataSource.insertSummoner(summoner.toEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSummoner(name: String): Flow<Result<Unit>> = flow {
        localDataSource.deleteSummoner(name).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSummonerAll(): Flow<Result<Unit>> = flow {
        localDataSource.deleteSummonerAll().run {
            emit(Result.success(Unit))
        }
    }

    override fun getProfile(): Flow<Result<Profile?>> = flow {
        localDataSource.getProfile()?.also {
            emit(Result.success(it.toDomain()))
        } ?: emit(Result.success(null))
    }

    override fun insertProfile(profile: Profile): Flow<Result<Unit>> = flow {
        localDataSource.insertProfile(profile.toEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteProfile(): Flow<Result<Unit>> = flow {
        localDataSource.deleteProfile().run {
            emit(Result.success(Unit))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun requestSummoner(name: String): Flow<Result<Summoner>> = flow {
        try {
            val key = getAuthKey()
            val result = requestSummoner(name, key)?.also {
                with(localDataSource.getSummoner()) {
                    if (isNotEmpty()) {
                        forEach { entity ->
                            if (it.name == entity.name) {
                                localDataSource.updateSummoner(it.toEntity())
                            } else {
                                localDataSource.insertSummoner(it.toEntity())
                            }
                        }
                    } else {
                        localDataSource.insertSummoner(it.toEntity())
                    }
                }
                saveSearch(it.name)
            }
            result?.also {
                emit(Result.success(it))
            } ?: emit(Result.failure(Exception(AppError.NoMatchHistory.parse())))

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun readSummonerList(): Flow<Result<List<Summoner>>> = flow {
        try {
            val key = getAuthKey()
            val result = requestSummonerList(key)
            emit(Result.success(result))
        } catch (e: Exception) {
            emit(Result.success(localDataSource.getSummoner().toSummonerList()))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun refreshSummonerList(): Flow<Result<List<Summoner>>> = flow {
        try {
            val key = getAuthKey()
            val result = requestSummonerList(key)
            emit(Result.success(result))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun requestSpectator(name: String): Flow<Result<Spectator>> = flow {
        try {
            val key = getAuthKey()
            val summoner = remoteDataSource.requestSummoner(name, key)
            val spectator = remoteDataSource.requestSpectator(summoner.id, key)
            val spectatorResult = spectator?.let { response ->
                Spectator(
                    map = response.mapId.toMap(),
                    banChamp = response.bannedChampions.toBanChamp(),
                    redTeam = response.participants.filter { it.teamId.toTeam() == Team.RED }
                        .toDomain(),
                    blueTeam = response.participants.filter { it.teamId.toTeam() == Team.BLUE }
                        .toDomain()
                )
            }

            spectatorResult?.also {
                emit(Result.success(it))
            } ?: emit(Result.failure(Exception(AppError.NotPlaying.parse())))

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun requestSummoner(name: String, key: String): Summoner? {
        val summoner = remoteDataSource.requestSummoner(name, key)
        val league = remoteDataSource.requestLeague(summoner.id, key)
        val spectator = remoteDataSource.requestSpectator(summoner.id, key)
        var summonerResult: Summoner? = null

        league.forEach {
            if (it.queueType == QueueType.SOLO_RANK) {
                summonerResult = Summoner(
                    name = summoner.name,
                    level = summoner.summonerLevel.toString(),
                    icon = summoner.profileIconId.toIcon(),
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
        return summonerResult
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun requestSummonerList(key: String): List<Summoner> {
        return mutableListOf<Summoner>().apply {
            localDataSource.getSummoner().forEach { summonerEntity ->
                requestSummoner(summonerEntity.name, key)?.also {
                    if (it.name == summonerEntity.name) {
                        localDataSource.updateSummoner(it.toEntity())
                    } else {
                        localDataSource.insertSummoner(it.toEntity())
                    }
                    saveSearch(summonerEntity.name)
                    add(it)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun saveSearch(name: String) {
        localDataSource.getSearch().run {
            if (isEmpty()) {
                localDataSource.insertSearch(
                    SearchEntity(
                        name = name,
                        date = LocalDate.now().toString()
                    )
                )
            } else {
                forEach { entity ->
                    if (name == entity.name) {
                        localDataSource.updateSearch(
                            SearchEntity(
                                name = name,
                                date = LocalDate.now().toString()
                            )
                        )
                    } else {
                        localDataSource.insertSearch(
                            SearchEntity(
                                name = name,
                                date = LocalDate.now().toString()
                            )
                        )
                    }
                }
            }
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
    private suspend fun Long.toMap(): String = withContext(ioDispatcher) {
        localDataSource.getMaps().forEach {
            if (it.mapId == this@toMap.toString()) {
                return@withContext it.mapName
            }
        }
        return@withContext defaultText
    }

    private suspend fun Long.toChampInfo(): Pair<String, String> =
        withContext(ioDispatcher) {
            localDataSource.getChamps().forEach {
                if (this@toChampInfo == (-1).toLong()) return@withContext "NoBan" to "NoBan"
                if (it.key == this@toChampInfo.toString()) {
                    return@withContext it.name to it.image.full.toChampImage()
                }
            }
            return@withContext defaultText to defaultText
        }

    private suspend fun Long.toRuneStyle(): Spectator.SpectatorInfo.Rune =
        withContext(ioDispatcher) {
            localDataSource.getRunes().forEach {
                if (it.id == this@toRuneStyle) {
                    return@withContext Spectator.SpectatorInfo.Rune(it.name, it.icon)
                }
            }
            return@withContext Spectator.SpectatorInfo.Rune(defaultText, defaultText)
        }

    private suspend fun Long.toSpellImage(): String = withContext(ioDispatcher) {
        localDataSource.getSpells().forEach {
            if (it.key == this@toSpellImage.toString()) {
                return@withContext it.image.full.toSpellImage()
            }
        }
        return@withContext defaultText
    }

    private suspend fun getMainRune(perkStyle: Long, perks: Long): String =
        withContext(ioDispatcher) {
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
            return@withContext defaultText
        }

    private suspend fun getRunes(
        perkStyle: Long,
        subStyle: Long,
        perks: List<Long>
    ): List<Spectator.SpectatorInfo.Rune> = withContext(ioDispatcher) {
        val runeNames = MutableList(6) { Spectator.SpectatorInfo.Rune("", "") }

        localDataSource.getRunes().forEach {
            if (it.id == perkStyle) {
                it.slots.forEach { slot ->
                    slot.runes.forEach { rune ->
                        if (rune.id == perks[0]) {
                            runeNames[0] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[1]) {
                            runeNames[1] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[2]) {
                            runeNames[2] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[3]) {
                            runeNames[3] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                    }
                }
            } else if (it.id == subStyle) {
                it.slots.forEach { slot ->
                    slot.runes.forEach { rune ->
                        if (rune.id == perks[4]) {
                            runeNames[4] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[5]) {
                            runeNames[5] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                    }
                }
            }
        }
        return@withContext runeNames
    }

}