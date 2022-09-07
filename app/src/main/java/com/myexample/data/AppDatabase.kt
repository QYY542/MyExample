package com.myexample.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.myexample.data.MyData.MyData
import com.myexample.data.MyData.MyDataDao
import com.myexample.data.MyDiary.MyDiary
import com.myexample.data.MyDiary.MyDiaryDao

/*
  **Created by 24606 at 11:35 2022.
*/

//@Database(entities = [Detail::class], version = 1)entities为数据类,数据结构变化时version要增加1
//@TypeConverters(DetailTypeConverters::class,...)指定复杂数据格式转换器,多个转换器之间用逗号隔开
@Database(entities = [MyData::class, MyDiary::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMyDataDao(): MyDataDao
    abstract fun getMyDiaryDao(): MyDiaryDao
}