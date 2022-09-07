package com.myexample.presentation.note

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.myexample.data.MyData.MyData
import com.myexample.viewModel.MyViewModel

/*
  **Created by 24606 at 23:37 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: MyViewModel
) {
    viewModel.setNavControllerNumber(1)
    val state by viewModel.state.collectAsState()
    //Pager
    var pagerState = rememberPagerState(0)
    HorizontalPager(
        state = pagerState,
        count = 2
    ) { page ->
        when (page) {
            0 -> NoteHome(state)
            1 -> NoteInfo()
        }
    }


}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun NoteHome(
    state: List<MyData>
) {
    Text(text = "NoteHome")
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
                Card(
                    Modifier
                        .fillMaxWidth()

                        .padding(10.dp, 5.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    elevation = 6.dp,
                    backgroundColor = Color.Gray
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(Modifier.padding(10.dp, 8.dp)) {
                            Text(text = "${item.id}")
                            Text(text = item.title)
                            Text(text = item.detail)
                        }
                        var dd = false
                        Checkbox(checked = dd, onCheckedChange = {
                            dd = !dd
                        })

                    }

                }
            }

            stickyHeader {
                Text(text = "已完成")
            }
        }
    }
}

@Composable
fun NoteInfo() {
    Box(Modifier.fillMaxSize()) {
        Text(text = "NoteInfo", modifier = Modifier.align(Alignment.Center))
    }

}


