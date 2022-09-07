package com.myexample.presentation.test

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import com.myexample.R
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.test.naked_eye_3d.My3DTest
import com.myexample.utils.currentTime
import java.util.*

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
    My3DTest(viewModel)
    //测试输入法遮挡问题
//    inputting()
    //测试时间
//    timeTest()
}

@Composable
fun timeTest() {
    var year by remember {
        mutableStateOf(currentTime.year())
    }
    var month by remember {
        mutableStateOf(currentTime.month())
    }
    var day by remember {
        mutableStateOf(currentTime.day())
    }
    var hour by remember {
        mutableStateOf(currentTime.hour())
    }
    var minute by remember {
        mutableStateOf(currentTime.minute())
    }
    var formatTime by remember {
        mutableStateOf(currentTime.formatTime())
    }
    var yesterday by remember {
        mutableStateOf(currentTime.otherday(-2))
    }

    Column() {
        Button(onClick = {
            year = currentTime.year()
            month = currentTime.month()
            day = currentTime.day()
            hour = currentTime.hour()
            minute = currentTime.minute()
        }) {
            Text(text = "Get Time")
        }

        Text(text = "Current Time")
        Text(text = "$year--$month--$day--$hour--$minute")
        Text(text = formatTime)
        Text(text = "${yesterday}")

        val date1 = Date(2022, 9, 7)
        val date2 = Date(2022, 9, 5)
        Text(text = "${currentTime.daysBetween(date1, date2)}")

    }
}



