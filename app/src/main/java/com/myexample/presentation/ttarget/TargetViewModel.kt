package com.myexample.presentation.target

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexample.data.MyDiary.MyDiary
import com.myexample.data.MyTarget.MyTarget
import com.myexample.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
  **Created by 24606 at 11:55 2022.
*/

@HiltViewModel
class TargetViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {
    private val _state = MutableStateFlow<List<MyTarget>>(listOf())
    val state: StateFlow<List<MyTarget>> = _state

    fun getAllData() {
        viewModelScope.launch {
            myRepository.getAllDataTarget()
                .collect {
                    _state.value = it
                }
        }
    }

    fun insert(myTarget: MyTarget?) {
        viewModelScope.launch {
            myRepository.insertTarget(myTarget)
            getAllData()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            myRepository.deleteAllTarget()
            getAllData()
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            myRepository.deleteByIdTarget(id)
            getAllData()
        }
    }

    fun update(myTarget: MyTarget?) {
        viewModelScope.launch {
            myRepository.updateTarget(myTarget)
            getAllData()
        }
    }

    init {
        viewModelScope.launch {
            getAllData()
        }
    }
}