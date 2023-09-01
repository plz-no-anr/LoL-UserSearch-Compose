package com.plz.no.anr.lol.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.plz.no.anr.lol.data.di.CoroutineQualifiers
import com.plz.no.anr.lol.data.model.common.AppError
import com.plz.no.anr.lol.data.model.local.SearchEntity
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.toDomain
import com.plz.no.anr.lol.data.repository.local.DataStoreManager
import com.plz.no.anr.lol.data.repository.local.app.AppLocalDataSource
import com.plz.no.anr.lol.data.repository.local.search.SearchLocalDataSource
import com.plz.no.anr.lol.data.repository.local.summoner.SummonerLocalDataSource
import com.plz.no.anr.lol.data.repository.remote.RemoteDataSource
import com.plz.no.anr.lol.data.utils.QueueType
import com.plz.no.anr.lol.data.utils.toChampImage
import com.plz.no.anr.lol.data.utils.toEntity
import com.plz.no.anr.lol.data.utils.toIcon
import com.plz.no.anr.lol.data.utils.toSpellImage
import com.plz.no.anr.lol.data.utils.toSummonerList
import com.plz.no.anr.lol.domain.model.Spectator
import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.model.Team
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.LocalDate

internal class SummonerRepositoryImpl(
    @CoroutineQualifiers.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val appLocalDataSource: AppLocalDataSource,
    private val summonerLocalDataSource: SummonerLocalDataSource,
    private val searchLocalDataSource: SearchLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreManager: DataStoreManager,
) : SummonerRepository {

    private val defaultText = "Not Found"

    private suspend fun getAuthKey() = requireNotNull(dataStoreManager.apiKeyFlow.first()) {
        throw Exception(AppError.Forbidden.parse())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun requestSummoner(name: String): Flow<Result<Summoner>> = flow {
        try {
            val key = getAuthKey()
            val result = requestSummoner(name, key)?.also {
                with(summonerLocalDataSource.getSummoner()) {
                    if (isNotEmpty()) {
                        forEach { entity ->
                            if (it.name == entity.name) {
                                summonerLocalDataSource.updateSummoner(it.toEntity())
                            } else {
                                summonerLocalDataSource.insertSummoner(it.toEntity())
                            }
                        }
                    } else {
                        summonerLocalDataSource.insertSummoner(it.toEntity())
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
            emit(Result.success(summonerLocalDataSource.getSummoner().toSummonerList()))
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
            val summoner = remoteDataSource.requestSummoner(key.toHeader(), name)
            val spectator = remoteDataSource.requestSpectator(key.toHeader(), summoner.id)
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

    override fun insertSummoner(summoner: Summoner): Flow<Result<Unit>> = flow {
        summonerLocalDataSource.insertSummoner(summoner.toEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSummoner(name: String): Flow<Result<Unit>> = flow {
        summonerLocalDataSource.deleteSummoner(name).run {
            emit(Result.success(Unit))
        }
    }

    override fun deleteSummonerAll(): Flow<Result<Unit>> = flow {
        summonerLocalDataSource.deleteSummonerAll().run {
            emit(Result.success(Unit))
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun requestSummoner(name: String, key: String): Summoner? {
        val header = key.toHeader()
        val summoner = remoteDataSource.requestSummoner(header, name)
        val league = remoteDataSource.requestLeague(header, key)
        val spectator = remoteDataSource.requestSpectator(header, key)

        league.forEach {
            if (it.queueType == QueueType.SOLO_RANK) {
                return Summoner(
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
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun requestSummonerList(key: String): List<Summoner> {
        return mutableListOf<Summoner>().apply {
            summonerLocalDataSource.getSummoner().forEach { summonerEntity ->
                requestSummoner(summonerEntity.name, key)?.also {
                    if (it.name == summonerEntity.name) {
                        summonerLocalDataSource.updateSummoner(it.toEntity())
                    } else {
                        summonerLocalDataSource.insertSummoner(it.toEntity())
                    }
                    saveSearch(summonerEntity.name)
                    add(it)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun saveSearch(name: String) {
        searchLocalDataSource.getSearch().run {
            if (isEmpty()) {
                searchLocalDataSource.insertSearch(
                    SearchEntity(
                        name = name,
                        date = LocalDate.now().toString()
                    )
                )
            } else {
                forEach { entity ->
                    if (name == entity.name) {
                        searchLocalDataSource.updateSearch(
                            SearchEntity(
                                name = name,
                                date = LocalDate.now().toString()
                            )
                        )
                    } else {
                        searchLocalDataSource.insertSearch(
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
        appLocalDataSource.getMaps().forEach {
            if (it.mapId == this@toMap.toString()) {
                return@withContext it.mapName
            }
        }
        return@withContext defaultText
    }

    private suspend fun Long.toChampInfo(): Pair<String, String> =
        withContext(ioDispatcher) {
            appLocalDataSource.getChamps().forEach {
                if (this@toChampInfo == (-1).toLong()) return@withContext "NoBan" to "NoBan"
                if (it.key == this@toChampInfo.toString()) {
                    return@withContext it.name to it.image.full.toChampImage()
                }
            }
            return@withContext defaultText to defaultText
        }

    private suspend fun Long.toRuneStyle(): Spectator.SpectatorInfo.Rune =
        withContext(ioDispatcher) {
            appLocalDataSource.getRunes().forEach {
                if (it.id == this@toRuneStyle) {
                    return@withContext Spectator.SpectatorInfo.Rune(it.name, it.icon)
                }
            }
            return@withContext Spectator.SpectatorInfo.Rune(defaultText, defaultText)
        }

    private suspend fun Long.toSpellImage(): String = withContext(ioDispatcher) {
        appLocalDataSource.getSpells().forEach {
            if (it.key == this@toSpellImage.toString()) {
                return@withContext it.image.full.toSpellImage()
            }
        }
        return@withContext defaultText
    }

    private suspend fun getMainRune(perkStyle: Long, perks: Long): String =
        withContext(ioDispatcher) {
            appLocalDataSource.getRunes().forEach {
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

        appLocalDataSource.getRunes().forEach {
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

    private fun String.toHeader() = HashMap<String, String>().apply {
        put("X-Riot-Token", this@toHeader)
    }

}