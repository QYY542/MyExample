package com.myexample.repository

import com.myexample.data.Detail
import com.myexample.data.DetailDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
  **Created by 24606 at 11:37 2022.
*/

@ViewModelScoped
class MyRepository @Inject constructor(
    private val detailDao: DetailDao
){
    fun getAllData(): Flow<List<Detail>> = detailDao.queryAll()

    suspend fun insert(detail: Detail?){
        detailDao.insert(detail)
    }

    suspend fun deleteAll(){
        detailDao.deleteAll()
    }

    suspend fun delete(detail: Detail?){
        if (detail != null) {
            detailDao.delete(detail)
        }
    }

    suspend fun update(detail: Detail?){
        if (detail != null) {
            detailDao.update(detail)
        }
    }


}