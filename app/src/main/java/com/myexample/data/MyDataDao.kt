package com.myexample.data

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

/*
  **Created by 24606 at 11:34 2022.
*/

@Dao
interface MyDataDao {
    @Query("SELECT * FROM details")
    fun getAll(): Flow<List<MyData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myData: MyData)

    @Query("DELETE FROM details")
    suspend fun deleteAll()

    @Query("DELETE FROM details where id = :id")
    suspend fun deleteById(id: Int)

    @Update
    suspend fun update(myData: MyData)


    //[Detail::class]为你创建的数据实体类
    //@return Flow<List<Detail>> 配合 jetpack compose 可以在数据变化时自动更新ui
    @RawQuery(observedEntities = [MyData::class])
    fun execsql(query: SupportSQLiteQuery): Flow<List<MyData>>
}