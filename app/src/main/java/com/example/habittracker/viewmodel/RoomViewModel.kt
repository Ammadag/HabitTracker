package com.example.habittracker.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.room.RvInfo
import com.example.habittracker.room.UserDB
import kotlinx.coroutines.launch


class RoomViewModel: ViewModel() {
    private val trackerDb: UserDB? = UserDB.INSTANCE



    fun insertData(userinfo: RvInfo) {
        viewModelScope.launch {
            trackerDb!!.userDao().insertTable(userinfo)
        }
    }

    fun getData(): LiveData<List<RvInfo?>> {
        return trackerDb!!.userDao().getAllItem()
    }

    fun updateData( userinfo: RvInfo) {
        viewModelScope.launch {
            trackerDb!!.userDao().updateTable(userinfo)
        }
    }

    fun deleteItem(id: Int){
        viewModelScope.launch {
            trackerDb!!.userDao().deleteById(id)
        }
    }
}