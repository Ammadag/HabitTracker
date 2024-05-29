package com.example.habittracker.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.room.StatsRvInfo
import com.example.habittracker.room.UserDB
import kotlinx.coroutines.launch


class StatsRoomVM: ViewModel() {
    private val StatsDataBase: UserDB? = UserDB.INSTANCE
    private val sts = StatsDataBase!!.statsDao()



    fun insertData(userinfo: StatsRvInfo) {
        viewModelScope.launch {
            sts.insertTable(userinfo)
        }
    }

    fun getData(): LiveData<List<StatsRvInfo?>> {
        return sts.getAllItem()
    }

    fun updateData(userinfo: StatsRvInfo) {
        viewModelScope.launch {
            sts.updateTable(userinfo)
        }
    }

    fun deleteItem(id: Int){
        viewModelScope.launch {
           sts.deleteById(id)
        }
    }
}