package com.example.habittracker.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.habittracker.room.RvInfo
import com.example.habittracker.room.UserDB
import kotlinx.coroutines.launch


class RoomViewModel: ViewModel() {
    private val TrackerDb: UserDB? = UserDB._INSTANCE



    fun insertData(userinfo: RvInfo) {
        viewModelScope.launch {
            TrackerDb!!.userDao().insertTable(userinfo)
        }
    }

    fun getData(): LiveData<List<RvInfo?>> {
        return TrackerDb!!.userDao().getAllItem()
    }

    fun updateData( userinfo: RvInfo) {
        viewModelScope.launch {
            TrackerDb!!.userDao().updateTable(userinfo)
        }
    }

    fun deleteItem(id: Int){
        viewModelScope.launch {
            TrackerDb!!.userDao().deleteById(id)
        }
    }
}