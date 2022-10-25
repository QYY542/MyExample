package com.myexample.presentation.Diary

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexample.data.MyDiary.MyDiary
import com.myexample.repository.MyRepository
import com.myexample.utils.currentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
  **Created by 24606 at 11:35 2022.
*/

@HiltViewModel
class DiaryViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {
    private val _state = MutableStateFlow<List<MyDiary>>(listOf())
    val state: StateFlow<List<MyDiary>> = _state

    fun getAllData() {
        viewModelScope.launch {
            myRepository.getAllDataDiray()
                .collect {
                    _state.value = it
                }
        }
    }

    private val _stateById = MutableStateFlow<MyDiary>(MyDiary())
    val stateById: StateFlow<MyDiary> = _stateById

    fun getById(id: Int) {
        _stateById.value = _state.value.sortedBy { it.date }.reversed().get(id)
    }

    fun insert(myDiary: MyDiary?) {
        viewModelScope.launch {
            myRepository.insertDiray(myDiary)
            getAllData()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            myRepository.deleteAllDiray()
            getAllData()
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            myRepository.deleteByIdDiray(id)
            getAllData()
        }
    }

    fun update(myDiary: MyDiary?) {
        viewModelScope.launch {
            myRepository.updateDiray(myDiary)
            getAllData()
        }
    }

    init {
        viewModelScope.launch {
            getAllData()
        }
    }

    //用于插入数据库
    val myDiary = mutableStateOf(MyDiary())

    fun changeMyDiary(item: MyDiary) {
        myDiary.value = item
        id.value = item.id
        title.value = item.title
        detail.value = item.detail
        date.value = item.date
        dateDetail.value = item.dateDetail
        mood.value = item.mood
    }

    //用于更新UI界面
    val id = mutableStateOf<Int?>(0)

    val title = mutableStateOf("")

    val detail = mutableStateOf("")

    val date = mutableStateOf("")

    val dateDetail = mutableStateOf(currentTime.formatTimeDetail())

    val mood = mutableStateOf(Mood.AWESOME)
}