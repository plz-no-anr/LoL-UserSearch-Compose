package com.plz.no.anr.lol.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.plz.no.anr.lol.data.model.common.AppError
import com.plz.no.anr.lol.data.model.local.asDomain
import com.plz.no.anr.lol.data.model.remote.SpectatorResponse
import com.plz.no.anr.lol.data.model.remote.asDomain
import com.plz.no.anr.lol.data.repository.local.DataStoreManager
import com.plz.no.anr.lol.data.repository.local.app.AppLocalDataSource
import com.plz.no.anr.lol.data.repository.local.summoner.SummonerLocalDataSource
import com.plz.no.anr.lol.data.repository.remote.RemoteDataSource
import com.plz.no.anr.lol.data.utils.QueueType
import com.plz.no.anr.lol.data.utils.asEntity
import com.plz.no.anr.lol.data.utils.asSummonerList
import com.plz.no.anr.lol.data.utils.catchResultError
import com.plz.no.anr.lol.data.utils.toChampImage
import com.plz.no.anr.lol.data.utils.toIcon
import com.plz.no.anr.lol.data.utils.toSpellImage
import com.plz.no.anr.lol.domain.model.Spectator
import com.plz.no.anr.lol.domain.model.Summoner
import com.plz.no.anr.lol.domain.model.Team
import com.plz.no.anr.lol.domain.repository.SummonerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

internal class SummonerRepositoryImpl(
    private val appLocalDataSource: AppLocalDataSource,
    private val summonerLocalDataSource: SummonerLocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val dataStoreManager: DataStoreManager,
) : SummonerRepository {

    private suspend fun authTokenHeader() = HashMap<String, String>().apply {
        val key = requireNotNull(dataStoreManager.apiKeyFlow.first()) {
            throw Exception(AppError.Forbidden.parse())
        }
        put("X-Riot-Token", key)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun requestSummoner(name: String): Flow<Result<Summoner>> = flow {
        val summoner = remoteDataSource.requestSummoner(
            authTokenHeader(),
            name
        ).getOrThrow()

        val league = remoteDataSource.requestLeague(
            authTokenHeader(),
            summoner.id
        ).getOrThrow()

        if (league.isNotEmpty()) {
            league.forEach {
                if (it.queueType == QueueType.SOLO_RANK) {
                    emit(
                        Result.success(
                            Summoner(
                                id = summoner.id,
                                name = summoner.name,
                                level = summoner.summonerLevel.toString(),
                                icon = summoner.profileIconId.toIcon(),
                                tier = it.tier,
                                leaguePoints = it.leaguePoints,
                                rank = it.rank,
                                wins = it.wins,
                                losses = it.losses,
                                miniSeries = it.miniSeries?.asDomain(),
                            )
                        )
                    )
                }
            }
        } else {
            emit(Result.failure(AppError.NoMatchHistory.exception()))
        }

    }.catchResultError()

    override fun getSummoner(name: String): Flow<Result<Summoner>> =
        summonerLocalDataSource.getSummoner(name)
            .map { entity ->
                entity?.let {
                    Result.success(it.asDomain())
                } ?: Result.failure(AppError.Default.exception())
            }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getSummonerList(): Flow<Result<List<Summoner>>> =
        summonerLocalDataSource.getSummonerList()
            .map { entity ->
                entity?.let {
                    Result.success(it.asSummonerList())
                } ?: Result.failure(AppError.Default.exception())
            }

    override fun requestSpectator(summonerId: String): Flow<Result<Spectator>> = flow {
        val result = remoteDataSource.requestSpectator(
            authTokenHeader(),
            summonerId
        )
        val spectator = result.getOrNull()?.let { response ->
            Spectator(
                map = response.mapId.toMap(),
                banChamp = response.bannedChampions.toBanChamp(),
                redTeam = response.participants.filter { it.teamId.toTeam() == Team.RED }
                    .asDomain(),
                blueTeam = response.participants.filter { it.teamId.toTeam() == Team.BLUE }
                    .asDomain()
            )
        }
        emit(
            spectator?.let {
                Result.success(it)
            } ?: Result.failure(Exception(AppError.NotPlaying.parse()))
        )
    }.catchResultError()

    override fun insertSummoner(summoner: Summoner): Flow<Result<Unit>> = flow {
        summonerLocalDataSource.insertSummoner(summoner.asEntity()).run {
            emit(Result.success(Unit))
        }
    }

    override fun updateSummoner(summoner: Summoner): Flow<Result<Unit>> = flow {
        summonerLocalDataSource.updateSummoner(summoner.asEntity()).run {
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

    private suspend fun List<SpectatorResponse.BannedChampion>.toBanChamp() = map {
        Spectator.BanChamp(
            team = it.teamId.toTeam(),
            champName = it.championId.toChampInfo().first
        )
    }

    private suspend fun List<SpectatorResponse.CurrentGameParticipant>.asDomain() = map {
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
    private suspend fun Long.toMap(): String =
        appLocalDataSource.getMaps()
            .map { maps ->
                maps.find { it.mapId == this.toString() }?.mapName
                    ?: throw Exception("Map Not Found")
            }.first()


    private suspend fun Long.toChampInfo(): Pair<String, String> =
        appLocalDataSource.getChamps()
            .map { champs ->
                if (this@toChampInfo == (-1).toLong()) return@map "NoBan" to "NoBan"
                champs.find { it.key == this.toString() }?.let {
                    it.name to it.image.full.toChampImage()
                } ?: ("Not Found" to "Not Found")
            }.first()

    private suspend fun Long.toRuneStyle(): Spectator.SpectatorInfo.Rune =
        appLocalDataSource.getRunes()
            .map { runes ->
                runes.find { it.id == this@toRuneStyle }?.let {
                    Spectator.SpectatorInfo.Rune(it.name, it.icon)
                } ?: throw Exception("Rune Not Found")
            }.first()

    private suspend fun Long.toSpellImage(): String =
        appLocalDataSource.getSpells()
            .map { spells ->
                spells.find { it.key == this.toString() }?.image?.full?.toSpellImage()
                    ?: throw Exception("Spell Not Found")
            }.first()

    private suspend fun getMainRune(perkStyle: Long, perks: Long): String =
        appLocalDataSource.getRunes()
            .map { runeEntities ->
                runeEntities.find { it.id == perkStyle }?.let { runeEntity ->
                    runeEntity.slots.map { slot ->
                        slot.runes
                    }.flatten()
                        .find { it.id == perks }?.icon ?: throw Exception("Main Rune Not Found")
                } ?: throw Exception("Main Rune Not Found")
            }.first()


    private suspend fun getRunes(
        perkStyle: Long,
        subStyle: Long,
        perks: List<Long>
    ): List<Spectator.SpectatorInfo.Rune> =
        appLocalDataSource.getRunes()
            .map { runeEntities ->
                val runeNames = mutableListOf<Spectator.SpectatorInfo.Rune>()
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
//                            if (rune.id == perks[index]) {
//                                runeNames.add(
//                                    index,
//                                    Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
//                                )
//                            }
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
                runeNames
            }.first()

}