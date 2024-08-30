package com.example.control.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "somethings")
data class Something(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    @ColumnInfo(name = "color")
    var typeColor: Int = 0,
    @ColumnInfo(name = "title")
    var title: String = "",
    @ColumnInfo(name = "time")
    var time: String = "",
    @ColumnInfo(name = "data")
    var data: String = "",
    @ColumnInfo(name = "day")
    var day: String = "",
    @ColumnInfo(name = "alarm")
    var alarm: Boolean = false
)
