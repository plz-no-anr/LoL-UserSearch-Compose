package com.plznoanr.lol.core.database.data.app

import com.plznoanr.lol.core.database.dao.JsonDao
import com.plznoanr.lol.core.database.model.json.ChampEntity
import com.plznoanr.lol.core.database.model.json.MapEntity
import com.plznoanr.lol.core.database.model.json.RuneEntity
import com.plznoanr.lol.core.database.model.json.SpellEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppLocalDataSourceImpl @Inject constructor(
    private val jsonDao: JsonDao
) : AppLocalDataSource {

    override suspend fun getChamps(): List<ChampEntity> {
        return jsonDao.getChamps()
    }

    override suspend fun getSpells(): List<SpellEntity> {
        return jsonDao.getSpells()
    }

    override suspend fun getRunes(): List<RuneEntity> {
        return jsonDao.getRunes()
    }

    override suspend fun getMaps(): List<MapEntity> {
        return jsonDao.getMaps()
    }

    override suspend fun insertChamp(champEntity: ChampEntity) {
        jsonDao.insertChamp(champEntity)
    }

    override suspend fun insertSpell(spellEntity: SpellEntity) {
        jsonDao.insertSpell(spellEntity)
    }

    override suspend fun insertRune(runeEntity: RuneEntity) {
        jsonDao.insertRune(runeEntity)
    }

    override suspend fun insertMap(mapEntity: MapEntity) {
        jsonDao.insertMap(mapEntity)
    }

    override suspend fun deleteAllLocalJson() {
        jsonDao.deleteChamps()
        jsonDao.deleteSpells()
        jsonDao.deleteRunes()
        jsonDao.deleteMaps()
    }

}