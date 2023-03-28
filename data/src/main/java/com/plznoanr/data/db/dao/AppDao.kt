package com.plznoanr.data.db.dao

import androidx.room.*
import com.plznoanr.data.model.local.ProfileEntity
import com.plznoanr.data.model.local.SearchEntity
import com.plznoanr.data.model.local.SummonerEntity

@Dao
interface AppDao {
    @Query("SELECT * FROM Search")
    suspend fun getSearch(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM Search WHERE name = :sName")
    suspend fun deleteSearch(sName: String)

    @Query("DELETE FROM Search")
    suspend fun deleteSearchAll()

    @Query("SELECT * FROM Profile")
    suspend fun getProfile() : ProfileEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profileEntity: ProfileEntity)

    @Query("DELETE FROM Profile")
    suspend fun deleteProfile()

    @Query("SELECT * FROM Summoner")
    suspend fun getSummoner() : List<SummonerEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummoner(summonerEntity: SummonerEntity)
    @Query("DELETE FROM Summoner WHERE name = :summonerName")
    suspend fun deleteSummoner(summonerName: String)

    @Query("DELETE FROM Summoner")
    suspend fun deleteSummonerAll()

}