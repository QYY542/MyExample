package com.myexample.repository

import com.myexample.data.MyData
import com.myexample.data.MyDataDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
  **Created by 24606 at 11:37 2022.
*/

@ViewModelScoped
class MyRepository @Inject constructor(
    private val myDataDao: MyDataDao
) {
    fun getAllData(): Flow<List<MyData>> = myDataDao.getAll()

    suspend fun insert(myData: MyData?) {
        if (myData != null) {
            myDataDao.insert(myData)
        }
    }

    suspend fun deleteAll() {
        myDataDao.deleteAll()
    }

    suspend fun deleteById(id: Int) {
        myDataDao.deleteById(id)
    }

    suspend fun update(myData: MyData?) {
        if (myData != null) {
            myDataDao.update(myData)
        }
    }


}