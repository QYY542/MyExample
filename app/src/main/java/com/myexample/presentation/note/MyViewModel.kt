package com.myexample.presentation.note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myexample.data.MyData.MyData
import com.myexample.repository.MyRepository
import com.myexample.utils.currentTime
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


    //第一页
    private val _state = MutableStateFlow<List<MyData>>(listOf())
    val state: StateFlow<List<MyData>> = _state

    fun getAllData() {
        viewModelScope.launch {
            myRepository.getAllData(false, currentTime.formatTime(), true)
                .collect {
                    _state.value = it
                }
        }
    }

    private val _stateNormal = MutableStateFlow<List<MyData>>(listOf())
    val stateNormal: StateFlow<List<MyData>> = _stateNormal

    fun getAllDataNormal() {
        viewModelScope.launch {
            myRepository.getAllData(false, currentTime.formatTime(), false)
                .collect {
                    _stateNormal.value = it
                }
        }
    }

    private val _stateCompleted = MutableStateFlow<List<MyData>>(listOf())
    val stateCompleted: StateFlow<List<MyData>> = _stateCompleted

    fun getAllDataCompleted() {
        viewModelScope.launch {
            myRepository.getAllDataCompleted(true, currentTime.formatTime())
                .collect {
                    _stateCompleted.value = it
                }
        }
    }

    //第二页
    private val _selectDate = mutableStateOf(currentTime.otherday(-1))
    val selectDate: State<String> = _selectDate

    fun get_2(selectDate: String) {
        getAllData_2(selectDate)
        getAllDataNormal_2(selectDate)
        getAllDataCompleted_2(selectDate)
    }

    private val _state_2 = MutableStateFlow<List<MyData>>(listOf())
    val state_2: StateFlow<List<MyData>> = _state_2

    fun getAllData_2(selectDate: String) {
        viewModelScope.launch {
            myRepository.getAllData(false, selectDate, true)
                .collect {
                    _state_2.value = it
                }
        }
    }

    private val _stateNormal_2 = MutableStateFlow<List<MyData>>(listOf())
    val stateNormal_2: StateFlow<List<MyData>> = _stateNormal_2

    fun getAllDataNormal_2(selectDate: String) {
        viewModelScope.launch {
            myRepository.getAllData(false, selectDate, false)
                .collect {
                    _stateNormal_2.value = it
                }
        }
    }

    private val _stateCompleted_2 = MutableStateFlow<List<MyData>>(listOf())
    val stateCompleted_2: StateFlow<List<MyData>> = _stateCompleted_2

    fun getAllDataCompleted_2(selectDate: String) {
        viewModelScope.launch {
            myRepository.getAllDataCompleted(true, selectDate)
                .collect {
                    _stateCompleted_2.value = it
                }
        }
    }


    fun insert(myData: MyData?) {
        viewModelScope.launch {
            myRepository.insert(myData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            myRepository.deleteAll()
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            myRepository.deleteById(id)
        }
    }

    fun update(myData: MyData?) {
        viewModelScope.launch {
            myRepository.update(myData)
        }
    }


    init {
        viewModelScope.launch {
            getAllData()
            getAllDataNormal()
            getAllDataCompleted()
//            getAllData_2(currentTime.otherday(-1))
//            getAllDataNormal_2(currentTime.otherday(-1))
//            getAllDataCompleted_2(currentTime.otherday(-1))
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

    //数据
    val id = mutableStateOf("")

    val title = mutableStateOf("")

    val detail = mutableStateOf("")

    val importance = mutableStateOf("false")

    val complete = mutableStateOf("false")

    val date = mutableStateOf("")


}

