package com.myexample.data.MyData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
  **Created by 24606 at 11:32 2022.
*/

//创建数据实体类
@Entity(tableName = "details")
data class MyData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,
    val title: String = "",
    val detail: String = "",
    val importance: Int = 0,
    val complete: Boolean = false,
    val date: String = "2021/1/1"

)