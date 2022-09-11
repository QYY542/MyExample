package com.myexample.data.MyDiary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myexample.presentation.diary.Mood

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
    var date: String = "2022-9-9",
    var dateDetail: String = "9,9,2022 15:20",
    var mood: Mood = Mood.AWESOME
)