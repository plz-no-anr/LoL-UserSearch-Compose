package com.plznoanr.data.utils

import android.content.Context
import com.plznoanr.domain.model.Spectator
import com.plznoanr.domain.model.common.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class JsonUtils(
    private val context: Context
) {

    fun getMap(mapId: Long): String {
        val mapString = context.assets.open(JsonFileName.MAP).reader().readText()
        val json = Json { ignoreUnknownKeys = true }
        val result: MapJson = json.decodeFromString(mapString)

        result.data.forEach { (key, value) ->
            if (key == mapId.toString()) {
                Timber.d("MapName: ${value.mapName}")
                return value.mapName
            }
        }

        return "Not Found"
    }

    fun getChampInfo(champId: Long): Pair<String, String> {
        if (champId == (-1).toLong()) return "NoBan" to "NoBan"

        try {
            val champString = context.assets.open(JsonFileName.CHAMPION).reader().readText()
            val json = Json { ignoreUnknownKeys = true }
            val result: ChampionJson = json.decodeFromString(champString)

            result.data.values.toList().forEach {
                if (it.key == champId.toString()) {
                    Timber.d("ChampName: ${it.name}, ChampImage: ${it.image.full.toChampImage()}")
                    return it.name to it.image.full.toChampImage()
                }
            }

        } catch (e: Exception) {
            Timber.e(e)
            return "Not Found" to "Not Found"
        }

        return "Not Found" to "Not Found"
    }

    fun getRuneStyle(perkStyle: Long): Spectator.SpectatorInfo.Rune {
        try {
            val runeString = context.assets.open(JsonFileName.RUNE).reader().readText()
            val json = Json { ignoreUnknownKeys = true }
            val result: List<RuneJson> = json.decodeFromString(runeString)

            result.forEach {
                if (it.id == perkStyle) {
                    Timber.d("RuneStyle:${it.icon}")
                    return Spectator.SpectatorInfo.Rune(
                        it.name,
                        it.icon
                    )
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            return Spectator.SpectatorInfo.Rune("Not Found", "Not Found")
        }

        return Spectator.SpectatorInfo.Rune("Not Found", "Not Found")
    }

    fun getMainRune(perkStyle: Long, perks: Long): String {

        val runeString = context.assets.open(JsonFileName.RUNE).reader().readText()
        val json = Json { ignoreUnknownKeys = true }
        val result: List<RuneJson> = json.decodeFromString(runeString)

        result.forEach {
            if (it.id == perkStyle) {
                it.slots.forEach { slot ->
                    slot.runes.forEach { rune ->
                        if (rune.id == perks) {
                            Timber.d("MainRune: ${rune.icon}")
                            return rune.icon
                        }
                    }
                }
            }
        }
        return "Not Found"
    }

    fun getRunes(
        perkStyle: Long,
        subStyle: Long,
        perks: List<Long>
    ): List<Spectator.SpectatorInfo.Rune> {
        val runeNames = MutableList(6) { Spectator.SpectatorInfo.Rune("", "") }
        val runeString = context.assets.open(JsonFileName.RUNE).reader().readText()

        val json = Json { ignoreUnknownKeys = true }
        val result: List<RuneJson> = json.decodeFromString(runeString)

        result.forEach {
            if (it.id == perkStyle) {
                it.slots.forEach { slot ->
                    slot.runes.forEach { rune ->
                        if (rune.id == perks[0]) {
                            Timber.d("Rune1:${rune.icon}")
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
        return runeNames
    }

    fun getSpellImage(spellId: Long): String {
        try {
            val jsonString = context.assets.open(JsonFileName.SUMMONER).reader().readText()
            val json = Json { ignoreUnknownKeys = true }
            val result: SummonerJson = json.decodeFromString(jsonString)

            result.data.forEach {
                if (it.value.key == spellId.toString()) {
                    Timber.d("Spell: ${it.value.image.full.toSpellImage()}")
                    return it.value.image.full.toSpellImage()
                }
            }

        } catch (e: Exception) {
            Timber.e(e)
            return "Not Found"
        }
        return "Not Found"
    }
}