package com.myexample.presentation.Tasks

import androidx.compose.runtime.*
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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
class TaskViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    //第一页
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

    //
    val myData = mutableStateOf(MyData())

    fun changeMyNote(item: MyData) {
        myData.value = item
        id.value = item.id
        title.value = item.title
        detail.value = if (item.detail == "") "●" else item.detail
        importance.value = item.importance
        complete.value = item.complete
        date.value = item.date
        status.value = item.status
        priority.value = item.status.toPriority()
        detail_2.value = TextFieldValue(
            text = detail.value,
            selection = TextRange(detail.value.length)
        )
    }

    //数据
    val id = mutableStateOf<Int?>(0)

    val title = mutableStateOf("")

    val detail = mutableStateOf("")

    val importance = mutableStateOf(false)

    val complete = mutableStateOf(false)

    val date = mutableStateOf("")

    val status = mutableStateOf(Status.INCOMPLETED_GREEN)

    val priority = mutableStateOf(Priority.LOW)

    val detail_2 = mutableStateOf(
        TextFieldValue(
            text = detail.value,
            selection = TextRange(detail.value.length)
        )
    )

    //
    val selectTime = mutableStateOf(currentTime.formatTime())


}

