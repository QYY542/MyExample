package com.myexample.presentation.note

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myexample.utils.constant
import com.myexample.utils.currentTime

/*
  **Created by 24606 at 18:23 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteHome_2(
    viewModel: MyViewModel
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


    LaunchedEffect(key1 = refresh){
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "我的日程安排")
                Button(onClick = { showDataPicker = !showDataPicker }) {
                    Text(text = "查看具体日期")
                }
            }
        }
    ) {

        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                stickyHeader {
                    Spacer(modifier = Modifier.height(50.dp))
                }
                stickyHeader {
                    Text(text = "未完成")
                }
                itemsIndexed(taskToDo) { index, item ->
                    NoteCard(
                        modifier = Modifier.animateItemPlacement(),
                        item = item,
                        complete = true,
                        homeScreen = false,
                        viewModel = viewModel
                    )
                }

                stickyHeader {
                    Text(text = "已完成")
                }

                itemsIndexed(taskCompleted) { index, item ->
                    NoteCard(
                        modifier = Modifier.animateItemPlacement(),
                        item = item,
                        complete = false,
                        homeScreen = false,
                        viewModel = viewModel
                    )
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

