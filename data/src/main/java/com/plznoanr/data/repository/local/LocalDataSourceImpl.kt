package com.plznoanr.data.repository.local

import com.plznoanr.data.db.dao.AppDao
import com.plznoanr.data.db.dao.JsonDao
import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.SummonerEntity
import com.plznoanr.data.model.local.json.ChampEntity
import com.plznoanr.data.model.local.json.MapEntity
import com.plznoanr.data.model.local.json.RuneEntity
import com.plznoanr.data.model.local.json.SpellEntity
class LocalDataSourceImpl (
    private val appDao: AppDao,
    private val jsonDao: JsonDao
) : LocalDataSource {
    override suspend fun getSearch(): List<SearchEntity> = appDao.getSearch()
    override suspend fun insertSearch(searchEntity: SearchEntity) {
        appDao.insertSearch(searchEntity)
    }

    override suspend fun updateSearch(searchEntity: SearchEntity) {
        appDao.updateSearch(searchEntity)
    }

    override suspend fun deleteSearch(sName: String) {
        appDao.deleteSearch(sName)
    }

    override suspend fun deleteSearchAll() {
        appDao.deleteSearchAll()
    }

    override suspend fun getProfile(): ProfileEntity = appDao.getProfile()

    override suspend fun insertProfile(profileEntity: ProfileEntity) {
        appDao.insertProfile(profileEntity)
    }

    override suspend fun updateProfile(profileEntity: ProfileEntity) {
        appDao.updateProfile(profileEntity)
    }

    override suspend fun deleteProfile() {
        appDao.deleteProfile()
    }

    override suspend fun getSummoner(): List<SummonerEntity> = appDao.getSummoner()

    override suspend fun insertSummoner(summonerEntity: SummonerEntity) {
        appDao.insertSummoner(summonerEntity)
    }

    override suspend fun updateSummoner(summonerEntity: SummonerEntity) {
        appDao.updateSummoner(summonerEntity)
    }

    override suspend fun deleteSummoner(name: String) {
        appDao.deleteSummoner(name)
    }

    override suspend fun deleteSummonerAll() {
        appDao.deleteSummonerAll()
    }

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