package com.plznoanr.lol.core.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.plznoanr.lol.core.common.di.AppDispatcher
import com.plznoanr.lol.core.common.di.Dispatcher
import com.plznoanr.lol.core.common.model.Paging
import com.plznoanr.lol.core.common.model.PagingList
import com.plznoanr.lol.core.common.model.Result
import com.plznoanr.lol.core.common.model.Result.ForbiddenError.exception
import com.plznoanr.lol.core.common.model.getOrNull
import com.plznoanr.lol.core.common.model.getOrThrow
import com.plznoanr.lol.core.data.utils.asEntity
import com.plznoanr.lol.core.data.utils.safeApiCall
import com.plznoanr.lol.core.data.utils.toChampImage
import com.plznoanr.lol.core.data.utils.toIcon
import com.plznoanr.lol.core.data.utils.toSpellImage
import com.plznoanr.lol.core.database.data.app.AppLocalDataSource
import com.plznoanr.lol.core.database.data.summoner.SummonerLocalDataSource
import com.plznoanr.lol.core.database.model.SummonerEntity
import com.plznoanr.lol.core.database.model.asDomain
import com.plznoanr.lol.core.datastore.SettingPreferenceDataSource
import com.plznoanr.lol.core.datastore.SummonerPreferenceDataSource
import com.plznoanr.lol.core.model.Nickname
import com.plznoanr.lol.core.model.Spectator
import com.plznoanr.lol.core.model.Summoner
import com.plznoanr.lol.core.model.Team
import com.plznoanr.lol.core.network.NetworkDataSource
import com.plznoanr.lol.core.network.model.SpectatorResponse
import com.plznoanr.lol.core.network.model.asDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

interface SummonerRepository {

    suspend fun requestSummoner(nickname: Nickname): Result<Summoner>

    fun getSummoner(summonerId: String): Flow<Summoner?>

    fun getSummonerAll(): Flow<List<Summoner>>

    fun getSummonerList(paging: Paging): Flow<PagingList<Summoner>>

    fun getBookMarkedSummonerIds(): Flow<Set<String>>

    suspend fun requestSpectator(summonerId: String): Result<Spectator>

    suspend fun upsertSummoner(summoner: Summoner)

    suspend fun updateBookmarkSummonerId(id: String)

    suspend fun clearBookmark()

    suspend fun deleteSummoner(name: String)

    suspend fun deleteSummonerAll()

}

class DefaultSummonerRepository @Inject constructor(
    private val appLocalDataSource: AppLocalDataSource,
    private val summonerLocalDataSource: SummonerLocalDataSource,
    private val networkDataSource: NetworkDataSource,
    private val settingPreferenceDataSource: SettingPreferenceDataSource,
    private val summonerPreferenceDataSource: SummonerPreferenceDataSource,
    @Dispatcher(AppDispatcher.IO) private val ioDispatcher: CoroutineDispatcher
) : SummonerRepository {

    private suspend fun authTokenHeader() = HashMap<String, String>().apply {
        val key = requireNotNull(settingPreferenceDataSource.apiKeyFlow.first()) {
            throw Result.ApiKeyNotFoundError.exception()
        }
        put("X-Riot-Token", key)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun requestSummoner(nickname: Nickname): Result<Summoner> =
        safeApiCall {
            val account = networkDataSource.requestAccount(
                authTokenHeader(),
                nickname.name,
                nickname.tag
            ).getOrThrow()

            val summoner = networkDataSource.requestSummoner(
                authTokenHeader(),
                account.puuid
            ).getOrThrow()

            val league = networkDataSource.requestLeague(
                authTokenHeader(),
                summoner.id
            ).getOrThrow()

            return@safeApiCall if (league.isNotEmpty()) {
                league.find { it.queueType == "RANKED_SOLO_5x5" }?.let { // 솔로 랭크만
                    val bookmarkedList = getBookMarkedSummonerIds().first()
                    Result.Success(
                        Summoner(
                            id = summoner.id,
                            nickname = Nickname(account.gameName, account.tagLine),
                            level = summoner.summonerLevel.toString(),
                            icon = summoner.profileIconId.toIcon(),
                            tier = it.tier,
                            leaguePoints = it.leaguePoints,
                            rank = it.rank,
                            wins = it.wins,
                            losses = it.losses,
                            miniSeries = it.miniSeries?.asDomain(),
                            isBookMarked = bookmarkedList.contains(summoner.id)
                        )
                    )
                } ?: Result.NoMatchHistoryError // 자랭만 있음
            } else {
                Result.NoMatchHistoryError // 매치 정보 x
            }
        }

    override fun getSummoner(summonerId: String): Flow<Summoner?> =
        summonerLocalDataSource.getSummoner(summonerId)
            .map { entity ->
                entity?.asDomain()
            }

    override fun getSummonerAll(): Flow<List<Summoner>> =
        summonerLocalDataSource.getSummonerAll().map { entities ->
            entities?.map(SummonerEntity::asDomain) ?: emptyList()
        }

    override fun getSummonerList(paging: Paging): Flow<PagingList<Summoner>> =
        summonerLocalDataSource.getSummonerList(
            page = paging.page,
            size = paging.size
        ).map { entities ->
            entities?.map(SummonerEntity::asDomain) ?: emptyList()
        }.map {
            PagingList(
                data = it,
                page = paging.page,
                size = paging.size,
                hasNext = it.size == paging.size
            )
        }

    override fun getBookMarkedSummonerIds(): Flow<Set<String>> =
        summonerPreferenceDataSource.bookmarkIdsFlow.onEach {
            Timber.d("getBookMarkedSummonerIds : $it")
        }

    override suspend fun requestSpectator(summonerId: String): Result<Spectator> = safeApiCall {
        val result = networkDataSource.requestSpectator(
            authTokenHeader(),
            summonerId
        )
        val spectator = result.getOrNull()?.let { response ->
            Spectator(
                map = response.mapId.toMap(),
                banChamp = response.bannedChampions.toBanChamp(),
                redTeam = response.participants.filter { it.teamId.toTeam() == Team.RED }
                    .map { it.asDomain() },
                blueTeam = response.participants.filter { it.teamId.toTeam() == Team.BLUE }
                    .map { it.asDomain() }
            )
        }

        return@safeApiCall if (spectator != null) {
            Result.Success(spectator)
        } else {
            Result.NotPlayingError
        }
    }

    override suspend fun upsertSummoner(summoner: Summoner) {
        withContext(ioDispatcher) {
            summonerLocalDataSource.upsertSummoner(summoner.asEntity())
        }
    }

    override suspend fun updateBookmarkSummonerId(id: String) {
        val bookmarkIds = summonerPreferenceDataSource.bookmarkIdsFlow.first()
        if (!bookmarkIds.contains(id)) {
            summonerPreferenceDataSource.updateBookmarkId(
                bookmarkIds = bookmarkIds.plus(id),
            )
        } else {
            summonerPreferenceDataSource.updateBookmarkId(
                bookmarkIds = bookmarkIds.minus(id)
            )
        }
    }

    override suspend fun clearBookmark() {
        summonerPreferenceDataSource.clearBookmark()
    }

    override suspend fun deleteSummoner(name: String) {
        withContext(ioDispatcher) {
            summonerLocalDataSource.deleteSummoner(name)
        }
    }

    override suspend fun deleteSummonerAll() {
        withContext(ioDispatcher) {
            launch { summonerPreferenceDataSource.clearBookmark() }
            launch { summonerLocalDataSource.deleteSummonerAll() }
        }
    }

    private suspend fun List<SpectatorResponse.BannedChampion>.toBanChamp() = map {
        Spectator.BanChamp(
            team = it.teamId.toTeam(),
            champName = it.championId.toChampInfo().first
        )
    }

    private suspend fun SpectatorResponse.CurrentGameParticipant.asDomain(): Spectator.SpectatorInfo {
        val champInfo = championId.toChampInfo()
        return Spectator.SpectatorInfo(
            name = summonerName,
            champName = champInfo.first,
            champImg = champInfo.second,
            team = teamId.toTeam(),
            spell1 = spell1Id.toSpellImage(),
            spell2 = spell2Id.toSpellImage(),
            runeStyle = perks.perkStyle.toRuneStyle(),
            subStyle = perks.perkSubStyle.toRuneStyle(),
            mainRune = getMainRune(perks.perkStyle, perks.perkIds[0]),
            rune = getRunes(perks.perkStyle, perks.perkSubStyle, perks.perkIds),
        )
    }

    private fun Long.toTeam() = if (this.toString() == "100") Team.BLUE else Team.RED

    private suspend fun Long.toMap(): String =
        appLocalDataSource.getMaps()
            .find { it.mapId == this.toString() }?.mapName
            ?: throw Exception("Map Not Found")

    private suspend fun Long.toChampInfo(): Pair<String, String> =
        if (this@toChampInfo == (-1).toLong()) {
            "NoBan" to "NoBan"
        } else {
            appLocalDataSource.getChamps()
                .find { it.key == this.toString() }?.let {
                    it.name to it.image.full.toChampImage()
                } ?: ("Not Found" to "Not Found")
        }

    private suspend fun Long.toRuneStyle(): Spectator.SpectatorInfo.Rune =
        appLocalDataSource.getRunes()
            .find { it.id == this@toRuneStyle }?.let {
                Spectator.SpectatorInfo.Rune(it.name, it.icon)
            } ?: throw Exception("Rune Not Found")


    private suspend fun Long.toSpellImage(): String =
        appLocalDataSource.getSpells()
            .find { it.key == this.toString() }?.image?.full?.toSpellImage()
            ?: throw Exception("Spell Not Found")


    private suspend fun getMainRune(perkStyle: Long, perks: Long): String =
        appLocalDataSource.getRunes()
            .find { it.id == perkStyle }?.let { runeEntity ->
                runeEntity.slots.map { slot ->
                    slot.runes
                }.flatten()
                    .find { it.id == perks }?.icon ?: throw Exception("Main Rune Not Found")
            } ?: throw Exception("Main Rune Not Found")


    private suspend fun getRunes(
        perkStyle: Long,
        subStyle: Long,
        perks: List<Long>
    ): List<Spectator.SpectatorInfo.Rune> {
        val runeNames = mutableListOf<Spectator.SpectatorInfo.Rune>()

        val runeEntities = appLocalDataSource.getRunes()

        runeEntities.find { it.id == perkStyle }?.let { runeEntity ->
            runeEntity.slots.map { it.runes }
                .flatten()
                .forEachIndexed { index, rune ->
                    perks.find { it == rune.id }.let {
                        runeNames.add(
                            index,
                            Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        )
                    }
                    if (index > 3) {
                        return@forEachIndexed
                    }
                }
        } ?: runeEntities.find { it.id == subStyle }?.let { runeEntity ->
            runeEntity.slots.map { it.runes }
                .flatten()
                .forEachIndexed { index, rune ->
                    if (index in 4..5) {
                        perks.find { it == rune.id }.let {
                            runeNames.add(
                                index,
                                Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                            )
                        }
                    }
                }
        }
        return runeNames
    }

}