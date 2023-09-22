package com.plz.no.anr.lol.data.utils

import android.content.Context
import com.plz.no.anr.lol.data.di.CoroutineQualifiers
import com.plz.no.anr.lol.domain.model.common.LocalJson
import com.plz.no.anr.lol.domain.model.common.json.ChampionJson
import com.plz.no.anr.lol.domain.model.common.json.MapJson
import com.plz.no.anr.lol.domain.model.common.json.RuneJson
import com.plz.no.anr.lol.domain.model.common.json.SummonerJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class JsonUtils(
    private val context: Context,
    @CoroutineQualifiers.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }
    suspend fun getLocalJson(): LocalJson = runBlocking(defaultDispatcher + coroutineExceptionHandler) {
        val json = Json { ignoreUnknownKeys = true }

        val mapJsonJob = async {
            val map = context.assets.open(JsonFileName.MAP).reader().readText()
            json.decodeFromString<MapJson>(map)
        }
        val champJsonJob = async {
            val champ = context.assets.open(JsonFileName.CHAMPION).reader().readText()
            json.decodeFromString<ChampionJson>(champ)
        }
        val runeJsonJob = async {
            val rune = context.assets.open(JsonFileName.RUNE).reader().readText()
            json.decodeFromString<List<RuneJson>>(rune)
        }
        val summonerJsonJob = async {
            val summoner = context.assets.open(JsonFileName.SUMMONER).reader().readText()
            json.decodeFromString<SummonerJson>(summoner)
        }

        return@runBlocking LocalJson(
            champJsonJob.await(),
            mapJsonJob.await(),
            runeJsonJob.await(),
            summonerJsonJob.await()
        )
    }

}