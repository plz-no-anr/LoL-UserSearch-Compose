package com.plznoanr.lol.core.database.data.app

import com.plznoanr.lol.core.database.model.json.ChampEntity
import com.plznoanr.lol.core.database.model.json.MapEntity
import com.plznoanr.lol.core.database.model.json.RuneEntity
import com.plznoanr.lol.core.database.model.json.SpellEntity
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