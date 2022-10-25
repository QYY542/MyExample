package com.myexample.data.MyTarget

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

/*
  **Created by 24606 at 11:50 2022.
*/

@Dao
interface MyStatusDao {
    @Query("SELECT * FROM target")
    fun getAll(): Flow<List<MyTarget>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(myTarget: MyTarget)

    @Query("DELETE FROM target")
    suspend fun deleteAll()

    @Query("DELETE FROM target WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Update
    suspend fun update(myTarget: MyTarget)


    //[Detail::class]为你创建的数据实体类
    //@return Flow<List<Detail>> 配合 jetpack compose 可以在数据变化时自动更新ui
    @RawQuery(observedEntities = [MyTarget::class])
    fun execsql(query: SupportSQLiteQuery): Flow<List<MyTarget>>
}