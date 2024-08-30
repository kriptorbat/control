package com.example.control.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    //ввод данных в базу
    @Insert
    fun insertItem(something: Something)
    //получение данных
    @Query("SELECT * FROM somethings WHERE day = :day")
    fun getAllItems(day: String): Flow<List<Something>>
    //удаление данных по идентификатору
    @Query("DELETE FROM somethings WHERE id = :userId")
    fun delete(userId: Int)
    //удаление всех данных из таблицы
    @Query("DELETE FROM somethings")
    fun deleteAll()
    //обновление данных по идентификатору
    @Query("UPDATE somethings SET time = :newTime WHERE id=:userId")
    fun updateTime(newTime: String, userId: Int?)
    @Query("UPDATE somethings SET alarm = :newAlarm WHERE id=:userId")
    fun updateAlarm(newAlarm: Boolean, userId: Int?)
}