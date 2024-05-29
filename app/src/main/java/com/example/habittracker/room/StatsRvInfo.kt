package com.example.habittracker.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.sql.Time


@Entity
data class StatsRvInfo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val distance: String,
    val time: String,
    val date: String,
    val day: String
)