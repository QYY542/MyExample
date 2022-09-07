package com.myexample.data.MyDiary

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.myexample.data.MyData.MyData
import kotlinx.coroutines.flow.Flow

/*
  **Created by 24606 at 11:16 2022.
*/

@Dao
interface MyDiaryDao {
    @Query("SELECT * FROM diary")
    fun getAll(): Flow<List<MyDiary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myDiary: MyDiary)

    @Query("DELETE FROM diary")
    suspend fun deleteAll()

    @Query("DELETE FROM diary WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Update
    suspend fun update(myDiary: MyDiary)


    //[Detail::class]为你创建的数据实体类
    //@return Flow<List<Detail>> 配合 jetpack compose 可以在数据变化时自动更新ui
    @RawQuery(observedEntities = [MyDiary::class])
    fun execsql(query: SupportSQLiteQuery): Flow<List<MyDiary>>
}