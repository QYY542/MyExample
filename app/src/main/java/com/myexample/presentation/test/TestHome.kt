package com.myexample.presentation.test

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
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
//    My3DTest(viewModel)
    //测试输入法遮挡问题
//    inputting()
    //测试时间
//    timeTest()

    AutofocusTextFieldExample(
        "123"
    )
}

enum class CursorSelectionBehaviour {
    START, END, SELECT_ALL
}

@Composable
fun AutofocusTextFieldExample(
    initValue: String,
    behaviour: CursorSelectionBehaviour = CursorSelectionBehaviour.END
) {
    val direction = LocalLayoutDirection.current
    var tfv by remember {
        val selection = when (behaviour) {
            CursorSelectionBehaviour.START -> {
                if (direction == LayoutDirection.Ltr) TextRange.Zero else TextRange(initValue.length)
            }
            CursorSelectionBehaviour.END -> {
                if (direction == LayoutDirection.Ltr) TextRange(initValue.length) else TextRange.Zero
            }
            CursorSelectionBehaviour.SELECT_ALL -> TextRange(0, initValue.length)
        }
        val textFieldValue = TextFieldValue(text = initValue, selection = selection)
        mutableStateOf(textFieldValue)
    }
    val focusRequester = remember { FocusRequester.Default }
    TextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = tfv,
        onValueChange = { tfv = it }
    )
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
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



