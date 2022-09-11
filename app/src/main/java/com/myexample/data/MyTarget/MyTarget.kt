package com.myexample.data.MyTarget

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
  **Created by 24606 at 11:49 2022.
*/

@Entity(tableName = "target")
data class MyTarget(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,
    var personNum: Int = 1,
    var personList: PersonList = PersonList(),
    var gNum: Int = 1,
    var gList: GList = GList()
)

data class PersonList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,
    var title: String = "",
    var detail: String = "",
    var targetTitle: String = "",
    var targetDetail: String = "",
    var date: String = ""

)

data class GList(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id", typeAffinity = ColumnInfo.INTEGER)
    val id: Int? = null,
    var title: String = "",
    var detail: String = "",
    var date: String = ""
)