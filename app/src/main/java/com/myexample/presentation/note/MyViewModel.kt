package com.myexample.presentation.note

import androidx.compose.runtime.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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


    //第一页
    private val _state = MutableStateFlow<List<MyData>>(listOf())
    val state: StateFlow<List<MyData>> = _state

    private val _refresh = MutableStateFlow<Int>(0)
    val refresh: StateFlow<Int> = _refresh

    fun onRefresh() {
        _refresh.value++
    }

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
            onRefresh()
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            myRepository.deleteAll()
            onRefresh()
        }
    }

    fun deleteById(id: Int) {
        viewModelScope.launch {
            myRepository.deleteById(id)
            onRefresh()
        }
    }

    fun update(myData: MyData?) {
        viewModelScope.launch {
            myRepository.update(myData)
            onRefresh()
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

    //数据
    val id = mutableStateOf<Int?>(0)

    val title = mutableStateOf("")

    val detail = mutableStateOf("")

    val importance = mutableStateOf(false)

    val complete = mutableStateOf(false)

    val date = mutableStateOf("")

    val status = mutableStateOf(Status.INCOMPLETED_GREEN)

    val detail_2 = mutableStateOf(
        TextFieldValue(
            text = detail.value,
            selection = TextRange(detail.value.length)
        )
    )


}

