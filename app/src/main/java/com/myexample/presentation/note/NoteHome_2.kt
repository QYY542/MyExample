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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var selectDate by remember {
        mutableStateOf(currentTime.otherday(-1))
    }
    viewModel.get_2(selectDate)
    val state by viewModel.state_2.collectAsState()
    val stateNormal by viewModel.stateNormal_2.collectAsState()
    val stateCompleted by viewModel.stateCompleted_2.collectAsState()
    var showDataPicker by remember {
        mutableStateOf(false)
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
            LazyColumn() {
                stickyHeader {
                    Spacer(modifier = Modifier.height(50.dp))
                }
                stickyHeader {
                    Text(text = "未完成")
                }
                itemsIndexed(state) { index, item ->
                    NoteCard(
                        item = item,
                        complete = true,
                        importance = true,
                        homeScreen = false,
                        viewModel = viewModel
                    )
                }

                itemsIndexed(stateNormal) { index, item ->
                    NoteCard(
                        item = item,
                        complete = true,
                        importance = false,
                        homeScreen = false,
                        viewModel = viewModel
                    )
                }

                stickyHeader {
                    Text(text = "已完成")
                }

                itemsIndexed(stateCompleted) { index, item ->
                    NoteCard(
                        item = item,
                        complete = false,
                        importance = true,
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
                        val text1 = "$year-$month-$day"
                        selectDate = text1
                        viewModel.get_2(selectDate)
                        showDataPicker = !showDataPicker
                    }
                }
            }
        }
    }
}

