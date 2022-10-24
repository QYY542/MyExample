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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myexample.data.MyDiary.MyDiary
import com.myexample.data.MyTarget.MyTarget
import com.myexample.presentation.diary.DirayViewModel
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 0:39 2022.
*/

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun MyTarget(
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    val state by dirayViewModel.state.collectAsState()
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    var coroutineScope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        ButtonList3(dirayViewModel,sheetState)
        Text(text = "target")
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
                    val myData = MyDiary(id.toInt(),detail = detail,date=date)
                    dirayViewModel.insert(myData)
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
                    val myData = MyDiary(id.toInt(),detail = detail,date=date)
                    dirayViewModel.insert(myData)})
                TextField(value = detail, onValueChange = {
                    detail = it
                    val myData = MyDiary(id.toInt(),detail = detail,date=date)
                    dirayViewModel.insert(myData)
                })
                TextField(value = date, onValueChange = {
                    date = it
                    val myData = MyDiary(id.toInt(),detail = detail,date=date)
                    dirayViewModel.insert(myData)
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
                            item.id?.let { dirayViewModel.deleteById(it) }
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
fun ButtonList3(
    dirayViewModel: DirayViewModel,
    sheetState: ModalBottomSheetState
) {
    var id = remember {
        mutableStateOf(1)
    }
    Button(onClick = {
        val myData = MyDiary(id.value)
        dirayViewModel.insert(myData)
        id.value++
    }) {
        Text(text = "insert+${id.value}")
    }

    Button(onClick = {
        dirayViewModel.deleteAll()
    }) {
        Text(text = "delete_all")
    }

    Button(onClick = {
        val myData = MyDiary(3, "123", "553")
        dirayViewModel.update(myData)
    }) {
        Text(text = "update")
    }
}