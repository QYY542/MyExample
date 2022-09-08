package com.myexample.presentation.note

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.myexample.utils.constant

/*
  **Created by 24606 at 18:47 2022.
*/

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteHome(
    viewModel: MyViewModel
) {
    val state by viewModel.state.collectAsState()
    val refresh by viewModel.refresh.collectAsState()


    var taskToDo = state.filter {
        !it.complete && it.date == constant.currTime
    }
    var taskCompleted = state.filter {
        it.complete && it.date == constant.currTime
    }


    LaunchedEffect(key1 = refresh){
        taskToDo = state.filter {
            !it.complete && it.date == constant.currTime
        }
        taskCompleted = state.filter {
            it.complete && it.date == constant.currTime
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Text(text = "我的日程安排")
        }
    ) {

        LazyColumn() {
            stickyHeader {
                Spacer(modifier = Modifier.height(50.dp))
            }
            stickyHeader {
                Text(text = "未完成")
            }
            itemsIndexed(taskToDo) { index, item ->
                NoteCard(
                    item = item,
                    complete = true,
                    homeScreen = true,
                    viewModel = viewModel
                )
            }

            stickyHeader {
                Text(text = "已完成")
            }

            itemsIndexed(taskCompleted) { index, item ->
                NoteCard(
                    item = item,
                    complete = false,
                    homeScreen = true,
                    viewModel = viewModel
                )
            }

        }
    }
}