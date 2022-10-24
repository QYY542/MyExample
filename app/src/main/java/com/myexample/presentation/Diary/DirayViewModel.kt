package com.myexample.presentation.Diary

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexample.data.MyDiary.MyDiary
import com.myexample.repository.MyRepository
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

    private val _refresh = MutableStateFlow<Int>(0)
    val refresh: StateFlow<Int> = _refresh

    fun onRefresh() {
        _refresh.value++
    }

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
            onRefresh()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            myRepository.deleteAllDiray()
            getAllData()
            onRefresh()
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            myRepository.deleteByIdDiray(id)
            getAllData()
            onRefresh()
        }
    }

    fun update(myDiary: MyDiary?) {
        viewModelScope.launch {
            myRepository.updateDiray(myDiary)
            getAllData()
            onRefresh()
        }
    }

    init {
        viewModelScope.launch {
            getAllData()
            onRefresh()
        }
    }

    //
    var id: MutableState<Int?> = mutableStateOf(0)

    var title = mutableStateOf("")

    var detail = mutableStateOf("")

    var date = mutableStateOf("")
}