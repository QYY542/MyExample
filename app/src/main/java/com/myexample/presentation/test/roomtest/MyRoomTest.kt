package com.myexample.presentation.test.roomtest

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.presentation.note.*
import com.myexample.presentation.ui.theme.ColorBACK
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.constant
import com.myexample.utils.currentTime
import com.myexample.utils.vibrate
import com.myexample.utils.vibrate_2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 16:56 2022.
*/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun MyRoomTest(
    viewModel: MyViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current
    var showDataPicker by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = sheetState.isVisible) {
        if (sheetState.isVisible) {
            kc?.show()
        } else {
            kc?.hide()
        }
    }
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        ButtonList(viewModel, sheetState)
        Text(text = "note")

        //滑板内变量
        val id = viewModel.id
        val title = viewModel.title
        val detail = viewModel.detail
        val date = viewModel.date
        val importance = viewModel.importance
        val complete = viewModel.complete
        val status = viewModel.status

        val myData = MyData(
            id.value,
            title.value,
            detail.value,
            importance.value,
            complete.value,
            date.value,
            status.value
        )

        //底部滑板
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetShape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
            sheetContent = {
                Button(onClick = {
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }) {}
                //输入框
                TextField(value = title.value.toString(), onValueChange = {
                    title.value = it
                    myData.title = title.value
                    viewModel.insert(myData)
                })
                TextField(value = detail.value, onValueChange = {
                    detail.value = it
                    myData.detail = detail.value
                    viewModel.insert(myData)
                })
                TextField(value = date.value, onValueChange = {
                    date.value = it
                    myData.date = date.value
                    viewModel.insert(myData)
                })
                //添加高度
                Spacer(modifier = Modifier.height(300.dp))
            }) {

            //列表
            LazyColumn(state = rememberLazyListState()) {
                items(items = state.sortedBy { it.status }) { item ->
                    Car2(Modifier, item, viewModel, onClick = {
                        //赋值
                        viewModel.id.value = item.id!!
                        viewModel.date.value = item.date
                        viewModel.title.value = item.title
                        viewModel.detail.value = item.detail
                        viewModel.complete.value = item.complete
                        viewModel.status.value = item.status
                        //展示
                        coroutineScope.launch {
                            if (sheetState.isVisible) {
                                sheetState.animateTo(ModalBottomSheetValue.Hidden)
                            } else {
                                sheetState.animateTo(ModalBottomSheetValue.Expanded)
                            }
                        }
                    })
                }
            }

        }
    }
}

//列表元素模板
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Car(
    item: MyData,
    sheetState: ModalBottomSheetState,
    viewModel: MyViewModel,
) {
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()

    val myData = MyData(
        item.id,
        item.title,
        item.detail,
        item.importance,
        item.complete,
        item.date,
        item.status
    )

    Row() {
        Column(Modifier.clickable {
            //赋值
            viewModel.id.value = item.id!!
            viewModel.date.value = item.date
            viewModel.title.value = item.title
            viewModel.detail.value = item.detail
            viewModel.complete.value = item.complete
            viewModel.status.value = item.status
            //展示
            coroutineScope.launch {
                sheetState.show()
            }

        }) {
            Text(text = item.id.toString())
            Text(text = item.date)
            Text(text = item.detail)
            //按钮
            androidx.compose.material3.IconButton(onClick = {
                vibrate_2(context)
                myData.status = when (myData.status) {
                    Status.INCOMPLETED_GREEN -> {
                        Status.INCOMPLETED_ORANGE
                    }
                    Status.INCOMPLETED_ORANGE -> {
                        Status.INCOMPLETED_RED
                    }
                    Status.INCOMPLETED_RED -> {
                        Status.INCOMPLETED_GREEN
                    }
                    else -> {
                        Status.INCOMPLETED_GREEN
                    }
                }
                viewModel.insert(myData)

            }, modifier = Modifier.size(30.dp)) {
                //图标
                Icon(
                    painterResource(item.status.icon),
                    item.status.title,
                    tint = item.status.color,
                    modifier = Modifier.size(30.dp)
                )

            }
        }
        Button(onClick = {
            item.id?.let { viewModel.deleteById(it) }
        }) {
            Text(text = "delete")
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun Car2(
    modifier: Modifier,
    item: MyData,
    viewModel: MyViewModel,
    onClick: (item: MyData) -> Unit
) {
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()

    val myData = MyData(
        item.id,
        item.title,
        item.detail,
        item.importance,
        item.complete,
        item.date,
        item.status
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
//                        onClick(MyData())
//                        onClick(item)
//                        constant.onAddButton = false
//                        constant.onAddButtonChange++
                        //逻辑在外面写，执行在这里
                        onClick(item)
                    },
                    onDoubleClick = {
                        vibrate_2(context)

                        myData.complete = !myData.complete
                        if (myData.complete) {
                            myData.status = Status.COMPLETED
                        } else {
                            myData.status = Status.INCOMPLETED_GREEN
                        }

                        viewModel.insert(myData)
                    },
                    onLongClick = {
                        vibrate(context)
//                    myData.importance = !myData.importance
                        myData.status = when (myData.status) {
                            Status.INCOMPLETED_GREEN -> {
                                Status.INCOMPLETED_ORANGE
                            }
                            Status.INCOMPLETED_ORANGE -> {
                                Status.INCOMPLETED_RED
                            }
                            Status.INCOMPLETED_RED -> {
                                Status.INCOMPLETED_GREEN
                            }
                            else -> {
                                Status.COMPLETED
                            }
                        }
                        viewModel.insert(myData)


                    }
                )
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.IconButton(onClick = {
                        vibrate_2(context)
                        myData.complete = !myData.complete
                        if (myData.complete) {
                            myData.status = Status.COMPLETED
                        } else {
                            myData.status = Status.INCOMPLETED_GREEN
                        }
                        viewModel.insert(myData)

                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(myData.status.icon),
                            myData.status.title,
                            tint = myData.status.color,
                            modifier = Modifier.size(30.dp)
                        )

                    }

                    Spacer(Modifier.width(8.dp))

                    MyTitleText(text = myData.title, complete = myData.complete)


                }
                var doubleClick by remember {
                    mutableStateOf(0)
                }
                LaunchedEffect(key1 = doubleClick) {
                    delay(300)
                    doubleClick = 0
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    androidx.compose.material3.IconButton(onClick = {
                        doubleClick++
                        vibrate_2(context)
                        if (doubleClick % 2 == 0) {
                            item.id?.let { viewModel.deleteById(it) }
                        }

                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(R.drawable.ic_delete),
                            myData.status.title,
                            tint = Red,
                            modifier = Modifier.size(30.dp)
                        )

                    }
                }
            }

            if (item.detail.isNotBlank() && item.detail != "●") {
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(8.dp))
                    MyDetialText(text = myData.detail, complete = myData.complete)
                    Spacer(Modifier.height(8.dp))
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
