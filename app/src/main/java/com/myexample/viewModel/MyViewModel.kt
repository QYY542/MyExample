package com.myexample.viewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.myexample.data.Detail
import com.myexample.repository.MyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

/*
  **Created by 24606 at 11:46 2022.
*/

@HiltViewModel
class MyViewModel @Inject constructor(
    private val myRepository: MyRepository
):ViewModel(){

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        
    }

}


data class HomeState(
    val users: List<Detail> = emptyList()
)
