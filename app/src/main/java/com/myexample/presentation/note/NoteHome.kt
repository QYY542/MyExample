package com.myexample.presentation.note

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.F
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnitType.Companion.Sp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mhss.app.mybrain.presentation.tasks.AddTaskBottomSheetContent
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.utils.constant
import com.myexample.utils.currentTime
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 18:47 2022.
*/

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteHome(
    viewModel: MyViewModel,
    onClick: (item: MyData) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val refresh by viewModel.refresh.collectAsState()


    var showDataPicker by remember {
        mutableStateOf(false)
    }


    var taskToDo = state.filter {
        !it.complete && it.date == constant.selectTime
    }
    var taskCompleted = state.filter {
        it.complete && it.date == constant.selectTime
    }


    LaunchedEffect(key1 = refresh) {
        taskToDo = state.filter {
            !it.complete && it.date == constant.selectTime
        }
        taskCompleted = state.filter {
            it.complete && it.date == constant.selectTime
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Tasks",
                            fontFamily = FontFamily(Font(R.font.rubik_bold)),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        Column(Modifier) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = constant.selectTime,
                                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.clickable {
                                    showDataPicker = !showDataPicker
                                }
                            )
                        }

                    }

                },
                backgroundColor = Color.White

            )
        }
    ) {

        var showIncompleted by remember {
            mutableStateOf(true)
        }
        var showCompleted by remember {
            mutableStateOf(true)
        }

        Box(Modifier.fillMaxSize()) {

            Column(Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(60.dp))
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    stickyHeader {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .clickable {
                                    showIncompleted = !showIncompleted
                                }
                        ) {
                            Text(
                                text = "未完成",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                    if (showIncompleted) {
                        itemsIndexed(taskToDo) { index, item ->
                            NoteCard(
                                modifier = Modifier,
                                item = item,
                                complete = true,
                                homeScreen = true,
                                viewModel = viewModel,
                                onClick = { onClick(it) },
                            )
                        }
                    }




                    stickyHeader {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .background(Color(241, 244, 252))
                                .clickable {
                                    showCompleted = !showCompleted
                                }
                        ) {
                            Text(
                                text = "已完成",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                    }

                    if (showCompleted) {
                        itemsIndexed(taskCompleted) { index, item ->
                            NoteCard(
                                modifier = Modifier,
                                item = item,
                                complete = false,
                                homeScreen = true,
                                viewModel = viewModel,
                                onClick = { onClick(it) }
                            )
                        }
                    }
                }
            }

            Box(Modifier.align(Alignment.Center)) {
                if (showDataPicker) {
                    DatePicker(
                        year = currentTime.year(),
                        month = currentTime.month(),
                        day = currentTime.day()
                    ) { selected, year, month, day ->
                        val selectTime = "$year-$month-$day"
                        constant.selectTime = selectTime
                        viewModel.onRefresh()
                        showDataPicker = !showDataPicker
                    }
                }
            }
        }
    }
}