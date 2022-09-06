package com.myexample.presentation.test

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myexample.data.MyData
import com.myexample.presentation.MyScreen
import com.myexample.presentation.test.inputting_taskbar.inputting
import com.myexample.presentation.test.naked_eye_3d.My3DTest
import com.myexample.presentation.test.roomtest.ButtonList
import com.myexample.presentation.test.roomtest.MyRoomTest
import com.myexample.viewModel.MyViewModel

/*
  **Created by 24606 at 16:42 2022.
*/


@Composable
fun TestHome(
    viewModel: MyViewModel
) {
    //测试Room数据库连接
//    MyRoomTest(viewModel)

    //测试3D效果
//    My3DTest(viewModel)
    
    //测试输入法遮挡问题
    inputting()
}



