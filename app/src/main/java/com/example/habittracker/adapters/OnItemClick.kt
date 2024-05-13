package com.example.habittracker.adapters

import com.example.habittracker.room.RvInfo

interface OnItemClick {
    fun onUpdate(userinfo: RvInfo)
    fun onDelete(id: Int)
}