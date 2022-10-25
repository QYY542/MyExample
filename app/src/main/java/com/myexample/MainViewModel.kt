package com.myexample

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.myexample.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/*
  **Created by 24606 at 22:49 2022.
*/

@HiltViewModel
class MainViewModel @Inject constructor(
    private val myRepository: MyRepository
) : ViewModel() {

    //当前页面
    private val _navController_Number = mutableStateOf(0)
    val navController_Number: State<Int> = _navController_Number

    fun setNavControllerNumber(num: Int) {
        _navController_Number.value = num
    }

    //是否点击了添加按钮
    var onButton = mutableStateOf(false)

}