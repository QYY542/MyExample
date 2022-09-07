package com.myexample.presentation.note

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

/*
  **Created by 24606 at 18:47 2022.
*/

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteHome(
    viewModel: MyViewModel
) {
    Text(text = "NoteHome")
    val state by viewModel.state.collectAsState()
    val stateNormal by viewModel.stateNormal.collectAsState()
    val stateCompleted by viewModel.stateCompleted.collectAsState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
        }
    ) {

        LazyColumn() {
            stickyHeader {
                Text(text = "我的日程安排")
            }
            stickyHeader {
                Text(text = "未完成")
            }
            itemsIndexed(state) { index, item ->
                NoteCard(
                    item = item,
                    complete = true,
                    importance = true,
                    homeScreen = true,
                    viewModel = viewModel
                )
            }

            itemsIndexed(stateNormal) { index, item ->
                NoteCard(
                    item = item,
                    complete = true,
                    importance = false,
                    homeScreen = true,
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
                    homeScreen = true,
                    viewModel = viewModel
                )
            }

        }
    }
}