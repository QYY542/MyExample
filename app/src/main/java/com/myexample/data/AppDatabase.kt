package com.myexample.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/*
  **Created by 24606 at 11:35 2022.
*/

//@Database(entities = [Detail::class], version = 1)entities为数据类,数据结构变化时version要增加1
//@TypeConverters(DetailTypeConverters::class,...)指定复杂数据格式转换器,多个转换器之间用逗号隔开
@Database(entities = [MyData::class], version = 1, exportSchema = false)
//@TypeConverters(DetailTypeConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMyDataDao(): MyDataDao
}