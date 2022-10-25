package com.myexample.data

import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.myexample.data.MyData.MyData
import com.myexample.data.MyData.MyDataDao
import com.myexample.data.MyDiary.MyDiary
import com.myexample.data.MyDiary.MyDiaryDao
import com.myexample.data.MyTarget.MyTarget
import com.myexample.data.MyTarget.MyStatusDao

/*
  **Created by 24606 at 11:35 2022.
*/


@TypeConverters(DetailTypeConverters::class)
@Database(
    entities = [MyData::class, MyDiary::class, MyTarget::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(@NonNull database: SupportSQLiteDatabase) {
            //执行升级相关操作
            //此处对于数据库中的所有更新都需要写下面的代码
            //添加某个字段
            database.execSQL(
                "ALTER TABLE target ADD COLUMN iss INTEGER"
            );
            //创建新表tb_compose并添加对应的字段
            //PRIMARY KEY(id)将id设置为主键，NOT NULL设置对应的键不能为空
//                database.execSQL("CREATE TABLE tb_compose(id INTEGER,testId INTEGER NOT NULL,
            //                createDate TEXT NOT NULL,updateDate TEXT NOT NULL,PRIMARY KEY(id))");
        }
    }


    abstract fun getMyDataDao(): MyDataDao
    abstract fun getMyDiaryDao(): MyDiaryDao
    abstract fun getMyTargetDao(): MyStatusDao
}