package com.example.habittracker.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface UserDao {
    @Query("SELECT * FROM RvInfo")
    fun getAllItem(): LiveData<List<RvInfo?>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTable(userInf: RvInfo)

    @Update(RvInfo::class)
    suspend fun updateTable(userInf: RvInfo)

    @Query("DELETE FROM RvInfo WHERE id = :id")
    suspend fun deleteById(id:Int)
}