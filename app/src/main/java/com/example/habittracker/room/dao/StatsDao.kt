package com.example.habittracker.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.habittracker.room.StatsRvInfo


@Dao
interface StatsDao {

    @Query("SELECT * FROM StatsRvInfo")
    fun getAllItem(): LiveData<List<StatsRvInfo?>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTable(statsInfo: StatsRvInfo)

    @Update(StatsRvInfo::class)
    suspend fun updateTable(userInf: StatsRvInfo)

    @Query("DELETE FROM StatsRvInfo WHERE id = :id")
    suspend fun deleteById(id:Int)

}