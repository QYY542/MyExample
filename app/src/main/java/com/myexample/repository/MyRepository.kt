package com.myexample.repository

import com.myexample.data.MyData.MyData
import com.myexample.data.MyData.MyDataDao
import com.myexample.data.MyDiary.MyDiary
import com.myexample.data.MyDiary.MyDiaryDao
import com.myexample.data.MyTarget.MyTarget
import com.myexample.data.MyTarget.MyTargetDao
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/*
  **Created by 24606 at 11:37 2022.
*/

@ViewModelScoped
class MyRepository @Inject constructor(
    private val myDataDao: MyDataDao,
    private val myDiaryDao: MyDiaryDao,
    private val myTargetDao: MyTargetDao
) {
    //MyData
    fun getAllData(complete: Boolean, date: String, importance: Boolean): Flow<List<MyData>> =
        myDataDao.getAll(complete, date, importance)


    fun getAllDataCompleted(complete: Boolean, date: String): Flow<List<MyData>> =
        myDataDao.getAllCompleted(complete, date)

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

    //MyDiray
    fun getAllDataDiray(): Flow<List<MyDiary>> = myDiaryDao.getAll()

    suspend fun insertDiray(myDiary: MyDiary?) {
        if (myDiary != null) {
            myDiaryDao.insert(myDiary)
        }
    }

    suspend fun deleteAllDiray() {
        myDiaryDao.deleteAll()
    }

    suspend fun deleteByIdDiray(id: Int) {
        myDiaryDao.deleteById(id)
    }

    suspend fun updateDiray(myDiary: MyDiary?) {
        if (myDiary != null) {
            myDiaryDao.update(myDiary)
        }
    }

    //MyTarget
    fun getAllDataTarget(): Flow<List<MyTarget>> = myTargetDao.getAll()

    suspend fun insertTarget(myTarget: MyTarget?) {
        if (myTarget != null) {
            myTargetDao.insert(myTarget)
        }
    }

    suspend fun deleteAllTarget() {
        myTargetDao.deleteAll()
    }

    suspend fun deleteByIdTarget(id: Int) {
        myTargetDao.deleteById(id)
    }

    suspend fun updateTarget(myTarget: MyTarget?) {
        if (myTarget != null) {
            myTargetDao.update(myTarget)
        }
    }

}