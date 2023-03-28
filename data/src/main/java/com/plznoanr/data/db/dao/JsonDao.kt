package com.plznoanr.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.plznoanr.data.model.local.json.ChampEntity
import com.plznoanr.data.model.local.json.MapEntity
import com.plznoanr.data.model.local.json.RuneEntity
import com.plznoanr.data.model.local.json.SpellEntity

@Dao
interface JsonDao {
    @Query("SELECT * FROM Champ")
    fun getChamps(): List<ChampEntity>

    @Query("SELECT * FROM Spell")
    fun getSpells(): List<SpellEntity>

    @Query("SELECT * FROM Rune")
    fun getRunes(): List<RuneEntity>

    @Query("SELECT * FROM Map")
    fun getMaps(): List<MapEntity>

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