package com.myexample.presentation.test

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.compose.rememberNavController
import com.myexample.presentation.Note.NoteViewModel
import com.myexample.presentation.test.roomtest.NavTest
import com.myexample.utils.currentTime
import java.util.*

/*
  **Created by 24606 at 16:42 2022.
*/


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TestHome(
    viewModel: NoteViewModel
) {
    //测试Room数据库连接
    val navController = rememberNavController()
//    MyRoomTest(viewModel)
    Scaffold(bottomBar = {
        Row() {
            Button(onClick = { navController.navigate("note_screen") }) {
                Text(text = "note_screen")
            }
            Button(onClick = { navController.navigate("diary_screen") }) {
                Text(text = "diary_screen")
            }
            Button(onClick = { navController.navigate("target_screen") }) {
                Text(text = "target_screen")
            }
        }
    }) {
        Column(Modifier.fillMaxSize()) {
            NavTest(navController = navController, viewModel = viewModel)
            Row() {
                Button(onClick = { navController.navigate("note_screen") }) {
                    Text(text = "note_screen")
                }
                Button(onClick = { navController.navigate("diary_screen") }) {
                    Text(text = "diary_screen")
                }
                Button(onClick = { navController.navigate("target_screen") }) {
                    Text(text = "target_screen")
                }
            }

        }
    }


    //测试3D效果
//    My3DTest(viewModel)
    //测试输入法遮挡问题
//    inputting()
    //测试时间
//    timeTest()

//    AutofocusTextFieldExample(
//        "123"
//    )
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



