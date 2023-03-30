package com.plznoanr.data.utils

import android.content.Context
import com.plznoanr.domain.model.common.*
import com.plznoanr.domain.model.common.json.ChampionJson
import com.plznoanr.domain.model.common.json.MapJson
import com.plznoanr.domain.model.common.json.RuneJson
import com.plznoanr.domain.model.common.json.SummonerJson
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import timber.log.Timber

class JsonUtils(
    private val context: Context
) {
    fun getLocalJson(): LocalJson? {
        try {
            val json = Json { ignoreUnknownKeys = true }
            val mapString = context.assets.open(JsonFileName.MAP).reader().readText()
            val champString = context.assets.open(JsonFileName.CHAMPION).reader().readText()
            val runeString = context.assets.open(JsonFileName.RUNE).reader().readText()
            val jsonString = context.assets.open(JsonFileName.SUMMONER).reader().readText()

            val mapJson = json.decodeFromString<MapJson>(mapString)
            val champJson = json.decodeFromString<ChampionJson>(champString)
            val runeJson = json.decodeFromString<List<RuneJson>>(runeString)
            val summonerJson = json.decodeFromString<SummonerJson>(jsonString)

            return LocalJson(champJson, mapJson, runeJson, summonerJson)
        } catch (e: Exception) {
            Timber.e(e)
        }
        return null
    }
}