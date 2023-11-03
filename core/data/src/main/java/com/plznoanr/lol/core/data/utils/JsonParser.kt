package com.plznoanr.lol.core.data.utils

import android.content.Context
import com.plznoanr.lol.core.common.di.AppDispatchers
import com.plznoanr.lol.core.data.model.LocalJson
import com.plznoanr.lol.core.data.model.ChampionJson
import com.plznoanr.lol.core.data.model.MapJson
import com.plznoanr.lol.core.data.model.RuneJson
import com.plznoanr.lol.core.data.model.SummonerJson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import timber.log.Timber

class JsonParser(
    private val context: Context,
    @AppDispatchers.Default private val coroutineDispatcher: CoroutineDispatcher
) {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    suspend fun getLocalJson(): LocalJson = runBlocking(coroutineDispatcher + coroutineExceptionHandler) {
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