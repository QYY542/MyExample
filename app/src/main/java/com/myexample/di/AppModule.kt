package com.myexample.di

import android.content.Context
import androidx.room.Room
import com.myexample.data.AppDatabase
import com.myexample.data.MyData.MyDataDao
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
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: AppDatabase): MyDataDao = database.getMyDataDao()
}