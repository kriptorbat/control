package com.example.control.room

class SomethingRepository(private val mainDb: MainDb) {

    fun getAllItems(day: String) = mainDb.getDao().getAllItems(day)

    suspend fun insertItem(something: Something){
        mainDb.getDao().insertItem(something)
    }

    suspend fun delete(userId: Int){
        mainDb.getDao().delete(userId)
    }
    suspend fun updateTime(newTime: String, userId: Int?){
        mainDb.getDao().updateTime(newTime,userId)
    }

}