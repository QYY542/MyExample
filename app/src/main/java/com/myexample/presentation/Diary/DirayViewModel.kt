package com.myexample.presentation.Diary

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexample.data.MyDiary.MyDiary
import com.myexample.repository.MyRepository
import com.myexample.utils.currentTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
  **Created by 24606 at 11:35 2022.
*/

@HiltViewModel
class DirayViewModel @Inject constructor(
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

    fun getById(idd: Int): Flow<MyDiary> {
        return myRepository.getByIdDiary(idd)
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

    //
    val myDiary = mutableStateOf(MyDiary())
    
    val id: MutableState<Int?> = mutableStateOf(0)

    val title = mutableStateOf("")

    val detail = mutableStateOf("")

    val date = mutableStateOf("")

    val dateDetail = mutableStateOf(currentTime.formatTimeDetail())

    val mood = mutableStateOf(Mood.AWESOME)
}