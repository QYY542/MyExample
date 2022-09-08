package com.myexample.presentation.diary

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material3.BottomAppBarDefaults.FloatingActionButtonElevation.elevation
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.note.NoteHome
import com.myexample.utils.vibrate
import com.myexample.utils.vibrate_2
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 23:37 2022.
*/

@OptIn(ExperimentalPagerApi::class)
@Composable
fun DiaryScreen(
    navController: NavController,
    viewModel: MyViewModel,
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    viewModel.setNavControllerNumber(3)

    //Pager
    var pagerState = rememberPagerState(0)
    HorizontalPager(
        state = pagerState,
        count = 2
    ) { page ->
        when (page) {
            0 -> DirayHome(navController)
            1 -> DiaryInfo()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DirayHome(
    navController: NavController,
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    val state by dirayViewModel.state.collectAsState()

    Column(Modifier.fillMaxSize()) {
        Button(onClick = {
            val myDiary = MyDiary()
            dirayViewModel.insert(myDiary)
        }) {
            Text(text = "Add Diary")
        }


        val listState = rememberLazyListState()

        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
            ) {
                itemsIndexed(state) { index, item ->
                    Card(
                        Modifier
                            .fillParentMaxWidth()
                            .height(150.dp)
                            .padding(10.dp, 5.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .combinedClickable(
                                onClick = {
                                    navController.navigate("diary_detail/${item.id}")
//                                    Log.d("++",item.id.toString())
                                },
                                onLongClick = {

                                }
                            ),
                        elevation = 6.dp,
                        backgroundColor = Color.Gray
                    ) {

                            Column(Modifier.padding(10.dp, 8.dp)) {
                                Text(text = item.title)
                                Text(text = item.detail)
                                Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                                    Text(text = item.dateDetail)
                                }
                            }

                    }
                }
            }
        }
    }
}

@Composable
fun DiaryInfo(
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    BoxText("DiaryInfo")
}

@Composable
fun BoxText(text: String) {
    Box(Modifier.fillMaxSize()) {
        Text(text = text, modifier = Modifier.align(Alignment.Center))
    }
}

