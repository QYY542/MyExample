package com.myexample.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexample.data.MyData.MyData
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
    private val _navController_Number = mutableStateOf(0)
    val navController_Number: State<Int> = _navController_Number

    fun setNavControllerNumber(num: Int) {
        _navController_Number.value = num
    }

    private val _title = mutableStateOf("")
    val title: State<String> = _title

    fun setTitle(title: String) {
        _title.value = title
    }


    private val _detail = mutableStateOf("")
    val detail: State<String> = _detail

    fun setDetail(detail: String) {
        _detail.value = detail
    }

    private val _importance = mutableStateOf(0)
    val importance: State<Int> = _importance

    fun setImportance(importance: Int) {
        _importance.value = importance
    }

}

