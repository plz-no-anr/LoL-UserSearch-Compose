package com.plz.no.anr.lol.data.repository.local.app

import com.plz.no.anr.lol.data.model.local.json.ChampEntity
import com.plz.no.anr.lol.data.model.local.json.MapEntity
import com.plz.no.anr.lol.data.model.local.json.RuneEntity
import com.plz.no.anr.lol.data.model.local.json.SpellEntity
import kotlinx.coroutines.flow.Flow

interface AppLocalDataSource {
    // Json
    suspend fun getChamps() : Flow<List<ChampEntity>>

    suspend fun getSpells() : Flow<List<SpellEntity>>

    suspend fun getRunes() : Flow<List<RuneEntity>>

    suspend fun getMaps() : Flow<List<MapEntity>>

    suspend fun insertChamp(champEntity: ChampEntity)

    suspend fun insertSpell(spellEntity: SpellEntity)

    suspend fun insertRune(runeEntity: RuneEntity)

    suspend fun insertMap(mapEntity: MapEntity)

    suspend fun deleteAllLocalJson()

}