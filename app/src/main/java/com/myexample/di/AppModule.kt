package com.myexample.di

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.myexample.data.AppDatabase
import com.myexample.data.MyData.MyDataDao
import com.myexample.data.MyDiary.MyDiaryDao
import com.myexample.data.MyTarget.MyTargetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
  **Created by 24606 at 11:32 2022.
*/

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context, AppDatabase::class.java,
        "DATABASE_NAME"
    ).addMigrations(MIGRATION_1_2).build()

    @Singleton
    @Provides
    fun provideMyDataDao(database: AppDatabase): MyDataDao = database.getMyDataDao()

    @Singleton
    @Provides
    fun provideMyDiaryDao(database: AppDatabase): MyDiaryDao = database.getMyDiaryDao()

    @Singleton
    @Provides
    fun provideMyTargetDao(database: AppDatabase): MyTargetDao = database.getMyTargetDao()


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
}