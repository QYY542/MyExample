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
    val title: String = "",
    val detail: String = "",
    val date: String = "2021/1/1"
)