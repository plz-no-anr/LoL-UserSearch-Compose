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
                Timber.d("맵: ${value.mapName}")
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
                    Timber.d("챔프이름: ${it.name}, 챔프이미지: ${it.image.full.toChampImage()}")
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
                    Timber.d("룬:${it.icon}")
                    return Spectator.SpectatorInfo.Rune(
                        it.name,
                        it.icon
                    )
                }
            }
//            val runeArray = JSONArray(runeString)
//            runeArray.forEach { rune ->
//                if (rune.getString("id") == perkStyle.toString()) {
//                    Timber.d("룬:${rune.getString("icon")}")
//                    style = Spectator.SpectatorInfo.Rune(
//                        rune.getString("name"),
//                        rune.getString("icon")
//                    )
//                }
//            }
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
                            Timber.d("룬2:${rune.icon}")
                            return rune.icon
                        }
                    }
                }
            }
        }

//        val runeArray = JSONArray(runeString)
//        runeArray.forEach {  rune ->
//            if (rune.getString("id") == perkStyle.toString()) {
//                val arr = rune.getJSONArray("slots")
//                val rArr = arr.getJSONObject(0).getJSONArray("runes")
//                rArr.forEach { r ->
//                    if (r.getString("id") == perks.toString()) {
//                        Timber.d("룬2:${r.getString("icon")}")
//                        runeName = r.getString("icon")
//                    }
//                }
//            }
//        }
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
                            Timber.d("룬3:${rune.icon}")
                            runeNames[0] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[1]) {
                            Timber.d("룬4:${rune.icon}")
                            runeNames[1] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[2]) {
                            Timber.d("룬5:${rune.icon}")
                            runeNames[2] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[3]) {
                            Timber.d("룬6:${rune.icon}")
                            runeNames[3] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                    }
                }
            }
            if (it.id == subStyle) {
                it.slots.forEach { slot ->
                    slot.runes.forEach { rune ->
                        if (rune.id == perks[4]) {
                            Timber.d("룬7:${rune.icon}")
                            runeNames[4] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                        if (rune.id == perks[5]) {
                            Timber.d("룬8:${rune.icon}")
                            runeNames[5] = Spectator.SpectatorInfo.Rune(rune.name, rune.icon)
                        }
                    }
                }
            }
        }

        val runeArray = JSONArray(runeString)
//        for (i in perks.indices) {
//            if (i < 4) {
//                runeNames[i] = runeArray.getRune(perks[i].toString(), perkStyle.toString())
//            } else if (i < 6) {
//                runeNames[i] = runeArray.getRune(subStyle.toString(), perks[i].toString())
//            }
//        }
        return runeNames
    }

    private fun JSONArray.getRune(id: String, style: String): Spectator.SpectatorInfo.Rune {
        var rune: Spectator.SpectatorInfo.Rune? = null
        this.forEach { runeObj ->
            if (runeObj.getString("id") == style) {
                val slots = runeObj.getJSONArray("slots")
                slots.forEach { slot ->
                    val runes = slot.getJSONArray("runes")
                    runes.forEach {
                        if (it.getString("id") == id) {
                            Timber.d("룬Icon:${it.getString("icon")}")
                            rune = Spectator.SpectatorInfo.Rune(
                                it.getString("name"),
                                it.getString("icon")
                            )
                        }
                    }
                }
            }
        }
        return rune ?: Spectator.SpectatorInfo.Rune("Not Found", "Not Found")
    }

    fun getSpellImage(spellId: Long): String {
        try {
            val jsonString = context.assets.open(JsonFileName.SUMMONER).reader().readText()
            val json = Json { ignoreUnknownKeys = true }
            val result: SummonerJson = json.decodeFromString(jsonString)

            result.data.forEach {
                if (it.key == spellId.toString()) {
                    Timber.d("스펠: ${it.value.image.full.toSpellImage()}")
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

private fun JSONArray.forEach(action: (JSONObject) -> Unit) {
    for (i in 0 until length()) {
        action(getJSONObject(i))
    }
}