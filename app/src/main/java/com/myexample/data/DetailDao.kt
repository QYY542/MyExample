package com.myexample.data

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

/*
  **Created by 24606 at 11:34 2022.
*/

@Dao
interface DetailDao {
    @Query("SELECT * FROM details")
    fun queryAll(): Flow<List<Detail>>

    @Insert
    fun insert(detail: Detail?)

    @Query("DELETE FROM details")
    fun deleteAll()

    @Update
    fun update(detail: Detail)

    @Delete
    fun delete(detail: Detail)


    //[Detail::class]为你创建的数据实体类
    //@return Flow<List<Detail>> 配合 jetpack compose 可以在数据变化时自动更新ui
    @RawQuery(observedEntities = [Detail::class])
    fun execsql(query: SupportSQLiteQuery) : Flow<List< Detail>>
}