package com.plznoanr.data.db

import androidx.room.*
import com.plznoanr.data.model.local.SearchEntity

@Dao
interface AppDao {
    @Query("SELECT * FROM Search")
    suspend fun getSearch(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchEntity: SearchEntity)

    @Query("DELETE FROM Search WHERE id = :uid")
    suspend fun deleteSearch(uid: Int)

    @Query("DELETE FROM Search")
    suspend fun deleteSearchAll()

}