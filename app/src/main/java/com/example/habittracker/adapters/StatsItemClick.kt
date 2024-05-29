package com.example.habittracker.adapters

import com.example.habittracker.room.StatsRvInfo

interface StatsItemClick {
    fun onUpdate(userinfo: StatsRvInfo)
    fun onDelete(id: Int)
}