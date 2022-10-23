package com.myexample

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


}