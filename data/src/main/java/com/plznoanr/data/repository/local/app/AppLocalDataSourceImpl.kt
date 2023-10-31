package com.plznoanr.data.repository.local.app

import com.plznoanr.data.db.dao.JsonDao
import com.plznoanr.data.model.local.json.ChampEntity
import com.plznoanr.data.model.local.json.MapEntity
import com.plznoanr.data.model.local.json.RuneEntity
import com.plznoanr.data.model.local.json.SpellEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppLocalDataSourceImpl @Inject constructor(
    private val jsonDao: JsonDao
) : AppLocalDataSource {

    override suspend fun getChamps(): Flow<List<ChampEntity>> {
        return jsonDao.getChamps()
    }

    override suspend fun getSpells(): Flow<List<SpellEntity>> {
        return jsonDao.getSpells()
    }

    override suspend fun getRunes(): Flow<List<RuneEntity>> {
        return jsonDao.getRunes()
    }

    override suspend fun getMaps(): Flow<List<MapEntity>> {
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