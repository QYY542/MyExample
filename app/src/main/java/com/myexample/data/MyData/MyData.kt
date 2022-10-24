package com.myexample.data.MyData

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.myexample.presentation.Note.Status

/*
  **Created by 24606 at 11:32 2022.
*/

//创建数据实体类
@Entity(tableName = "details")
data class MyData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,
    var title: String = "",
    var detail: String = "●",
    var importance: Boolean = false,
    var complete: Boolean = false,
    var date: String = "2022-1-1",
    var status: Status = Status.INCOMPLETED_GREEN
)