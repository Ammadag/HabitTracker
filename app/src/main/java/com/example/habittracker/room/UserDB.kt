package com.example.habittracker.room


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.habittracker.room.dao.StatsDao
import com.example.habittracker.room.dao.UserDao
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [(RvInfo::class),(StatsRvInfo::class)], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun statsDao(): StatsDao
    companion object {

        @Volatile
        var INSTANCE: UserDB? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getInstance(context: Context): UserDB {
            if (INSTANCE != null) return INSTANCE!!
            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(
                        context,
                        UserDB::class.java,
                        "todo_database"
                    )
                    .build()
                return INSTANCE!!
            }
        }
    }
}