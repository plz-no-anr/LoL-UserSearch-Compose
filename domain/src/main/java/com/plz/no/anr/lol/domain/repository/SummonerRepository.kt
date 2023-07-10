package com.plz.no.anr.lol.domain.repository

import com.plz.no.anr.lol.domain.model.Spectator
import com.plz.no.anr.lol.domain.model.Summoner
import kotlinx.coroutines.flow.Flow

interface SummonerRepository {

    fun requestSummoner(name: String): Flow<Result<Summoner>>

    fun readSummonerList(): Flow<Result<List<Summoner>>>

    fun refreshSummonerList(): Flow<Result<List<Summoner>>>

    fun requestSpectator(name: String): Flow<Result<Spectator>>

    fun insertSummoner(summoner: Summoner): Flow<Result<Unit>>

    fun deleteSummoner(name: String): Flow<Result<Unit>>

    fun deleteSummonerAll(): Flow<Result<Unit>>

}