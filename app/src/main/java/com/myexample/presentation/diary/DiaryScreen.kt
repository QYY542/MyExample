package com.myexample.presentation.diary

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
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
import com.myexample.presentation.note.NoteInfo
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
            0 -> DirayHome()
            1 -> DiaryInfo()
        }
    }
}

@Composable
fun DirayHome(
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
        var ItemIndex by remember {
            mutableStateOf(0)
        }
        var ifChange = 0

        var ifNextColumn by remember {
            mutableStateOf(0)
        }
        LaunchedEffect(key1 = ItemIndex) {
            listState.animateScrollToItem(index = ItemIndex)
        }
        var context = LocalContext.current
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                state = listState,
                userScrollEnabled = false,
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectVerticalDragGestures(
                            onVerticalDrag = { change, dragAmount ->
                                change.consumeAllChanges()
                                if (dragAmount > 10) {
                                    ifChange = -1
                                } else if (dragAmount < -10) {
                                    ifChange = 1
                                }
                            },
                            onDragEnd = {
                                //上下滑动
                                if (ifChange != 0) {
                                    ItemIndex += ifChange
                                    if (ItemIndex <= 0) {
                                        ItemIndex = 0
                                        vibrate_2(context)
                                    }
                                    if (ItemIndex >= state.size - 1) {
                                        ItemIndex = state.size - 1
                                    }
                                    vibrate(context)
                                    ifChange = 0
                                }
                            }
                        )
                    }
            ) {
                itemsIndexed(state) { index, item ->


                    Card(
                        Modifier
                            .fillParentMaxWidth()
                            .fillParentMaxHeight(0.9f)
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

