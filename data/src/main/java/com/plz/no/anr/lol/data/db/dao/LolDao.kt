package com.plz.no.anr.lol.data.db.dao

import androidx.room.*
import com.plz.no.anr.lol.data.model.local.ProfileEntity
import com.plz.no.anr.lol.data.model.local.SearchEntity
import com.plz.no.anr.lol.data.model.local.SummonerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LolDao {

    @Query("SELECT * FROM Search")
    fun getSearchList(): Flow<List<SearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM Search WHERE name = :sName")
    suspend fun deleteSearch(sName: String)

    @Update
    suspend fun updateSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM Search")
    suspend fun deleteSearchAll()

    @Query("SELECT * FROM Profile")
    fun getProfile() : Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProfile(profileEntity: ProfileEntity)

    @Update
    suspend fun updateProfile(profileEntity: ProfileEntity)

    @Query("DELETE FROM Profile")
    suspend fun deleteProfile()

    @Query("SELECT * FROM Summoner")
    fun getSummonerList() : Flow<List<SummonerEntity>?>

    @Query("SELECT * FROM Summoner WHERE name = :summonerName")
    fun getSummoner(summonerName: String) : Flow<SummonerEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSummoner(summonerEntity: SummonerEntity)

    @Update
    suspend fun updateSummoner(summonerEntity: SummonerEntity)

    @Query("DELETE FROM Summoner WHERE name = :summonerName")
    suspend fun deleteSummoner(summonerName: String)

    @Query("DELETE FROM Summoner")
    suspend fun deleteSummonerAll()

}