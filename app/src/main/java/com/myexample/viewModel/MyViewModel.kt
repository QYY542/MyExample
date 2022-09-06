package com.myexample.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexample.data.MyData
import com.myexample.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
  **Created by 24606 at 11:46 2022.
*/

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    private val _state = MutableStateFlow<List<MyData>>(listOf())
    val state: StateFlow<List<MyData>> = _state

    fun getAllData() {
        viewModelScope.launch {
            myRepository.getAllData()
                .collect {
                    _state.value = it
                }
        }
    }

    fun insert(myData: MyData?) {
        viewModelScope.launch {
            myRepository.insert(myData)
            getAllData()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            myRepository.deleteAll()
            getAllData()
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            myRepository.deleteById(id)
            getAllData()
        }
    }

    fun update(myData: MyData?) {
        viewModelScope.launch {
            myRepository.update(myData)
            getAllData()
        }
    }

    init {
        viewModelScope.launch {
            getAllData()
        }
    }

    //3D
    private val _x = MutableStateFlow<Float>(0F)
    val x: StateFlow<Float> = _x

    fun onxStateChanged(x: Float) {
        _x.value = x
    }

    private val _y = MutableStateFlow<Float>(0F)
    val y: StateFlow<Float> = _y

    fun onyStateChanged(y: Float) {
        _y.value = y
    }

    //全局
    val navController_Number = MutableStateFlow<Int>(0)

    fun setNavControllerNumber(num: Int) {
        navController_Number.value = num
    }

}

