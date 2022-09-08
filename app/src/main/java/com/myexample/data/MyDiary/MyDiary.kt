package com.myexample.data.MyDiary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
  **Created by 24606 at 11:15 2022.
*/

@Entity(tableName = "diary")
data class MyDiary(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,
    var title: String = "",
    var detail: String = "",
    var date: String = "2021-1-1",
    var dateDetail:String = "9月9日，2022 15:20"
)