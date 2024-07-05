package com.example.habittracker.room

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RvInfo(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val desc: String,
)


