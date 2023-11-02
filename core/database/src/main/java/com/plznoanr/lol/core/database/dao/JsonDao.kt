package com.plznoanr.lol.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plznoanr.lol.core.database.model.json.ChampEntity
import com.plznoanr.lol.core.database.model.json.MapEntity
import com.plznoanr.lol.core.database.model.json.RuneEntity
import com.plznoanr.lol.core.database.model.json.SpellEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JsonDao {
    @Query("SELECT * FROM Champ")
    fun getChamps(): Flow<List<ChampEntity>>

    @Query("SELECT * FROM Spell")
    fun getSpells(): Flow<List<SpellEntity>>

    @Query("SELECT * FROM Rune")
    fun getRunes(): Flow<List<RuneEntity>>

    @Query("SELECT * FROM Map")
    fun getMaps(): Flow<List<MapEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChamp(champEntity: ChampEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSpell(spellEntity: SpellEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRune(runeEntity: RuneEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMap(mapEntity: MapEntity)

    @Query("DELETE FROM Champ")
    fun deleteChamps()

    @Query("DELETE FROM Spell")
    fun deleteSpells()

    @Query("DELETE FROM Rune")
    fun deleteRunes()

    @Query("DELETE FROM Map")
    fun deleteMaps()
}