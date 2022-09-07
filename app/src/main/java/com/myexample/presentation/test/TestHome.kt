package com.myexample.presentation.test

import androidx.compose.runtime.*
import com.myexample.presentation.test.inputting_taskbar.inputting
import com.myexample.presentation.note.MyViewModel

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



