package com.myexample.presentation.test.roomtest

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.mhss.app.mybrain.presentation.tasks.AddTaskBottomSheetContent
import com.myexample.data.MyData.MyData
import com.myexample.presentation.note.MyViewModel
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 16:56 2022.
*/

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MyRoomTest(
    viewModel: MyViewModel
) {
    val state by viewModel.state.collectAsState()
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var coroutineScope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        ButtonList(viewModel,sheetState)

        var id by remember {
            mutableStateOf("1")
        }
        var detail by remember {
            mutableStateOf("1")
        }
        var date by remember {
            mutableStateOf("2022-1-1")
        }
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
            sheetContent = {
                Button(onClick = {
                    val myData = MyData(id.toInt(),detail = detail,date=date)
                    viewModel.insert(myData)
                    coroutineScope.launch {
                        sheetState.hide()
                        if (kc != null) {
                            kc.hide()
                        }
                    }
                }) {

                }
                TextField(value = id, onValueChange = {
                    id = it
                    val myData = MyData(id.toInt(),detail = detail,date=date)
                    viewModel.insert(myData)})
                TextField(value = detail, onValueChange = {
                    detail = it
                    val myData = MyData(id.toInt(),detail = detail,date=date)
                    viewModel.insert(myData)
                })
                TextField(value = date, onValueChange = {
                    date = it
                    val myData = MyData(id.toInt(),detail = detail,date=date)
                    viewModel.insert(myData)
                })
                Spacer(modifier = Modifier.height(300.dp))
            }){


        LazyColumn(state = rememberLazyListState()) {
            items(items = state) { item ->
                Row() {
                    Column(Modifier.clickable {
                        id = item.id.toString()
                        date = item.date
                        detail = item.detail
                        coroutineScope.launch {
                            sheetState.show()
                        }
                    }) {
                        Text(text = item.id.toString())
                        Text(text = item.date)
                        Text(text = item.detail)
                    }
                    Button(onClick = {
                        item.id?.let { viewModel.deleteById(it) }
                    }) {
                        Text(text = "delete")
                    }
                }
            }
        }
    }
}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonList(
    viewModel: MyViewModel,
    sheetState: ModalBottomSheetState
) {
    var id = remember {
        mutableStateOf(1)
    }
    Button(onClick = {
        val myData = MyData(id.value)
        viewModel.insert(myData)
        id.value++
    }) {
        Text(text = "insert+${id.value}")
    }

    Button(onClick = {
        viewModel.deleteAll()
    }) {
        Text(text = "delete_all")
    }

    Button(onClick = {
        val myData = MyData(3, "123", "553")
        viewModel.update(myData)
    }) {
        Text(text = "update")
    }
}
