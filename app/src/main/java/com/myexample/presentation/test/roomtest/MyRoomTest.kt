package com.myexample.presentation.test.roomtest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.myexample.data.MyData
import com.myexample.viewModel.MyViewModel

/*
  **Created by 24606 at 16:56 2022.
*/

@Composable
fun MyRoomTest(
    viewModel: MyViewModel
) {
    val state by viewModel.state.collectAsState()

    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        ButtonList(viewModel)

        Text(text = "Success")
        LazyColumn(state = rememberLazyListState()) {
            items(items = state) { item ->
                Row() {
                    Column() {
                        Text(text = item.id.toString())
                        Text(text = item.date)
                        Text(text = item.message)
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

@Composable
fun ButtonList(
    viewModel: MyViewModel
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
