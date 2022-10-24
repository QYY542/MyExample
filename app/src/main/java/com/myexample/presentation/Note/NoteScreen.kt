package com.myexample.presentation.Note

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.myexample.MainViewModel
import com.myexample.data.MyData.MyData
import com.myexample.presentation.ui.theme.ColorBACK
import com.myexample.utils.constant
import com.myexample.utils.currentTime

/*
  **Created by 24606 at 23:37 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    viewModel: NoteViewModel = hiltViewModel(),
    onClick: (item: MyData) -> Unit,
) {
    mainViewModel.setNavControllerNumber(1)
    navController.enableOnBackPressed(false)
    NoteHome(viewModel, onClick = {
        onClick(it)
    })
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class
)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteHome(
    viewModel: NoteViewModel,
    onClick: (item: MyData) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    var showDataPicker by remember {
        mutableStateOf(false)
    }

    Scaffold(
        backgroundColor = ColorBACK,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Tasks",
                            fontFamily = FontFamily(Font(com.myexample.R.font.rubik_bold)),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        Column(Modifier) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = constant.selectTime,
                                fontFamily = FontFamily(Font(com.myexample.R.font.rubik_bold)),
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
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
//                Spacer(modifier = Modifier.height(60.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    //in process
                    stickyHeader {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(ColorBACK)
                        ) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "in progress",
                                fontFamily = FontFamily(Font(com.myexample.R.font.rubik_bold)),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }


                    itemsIndexed(state.sortedBy {
                        it.status
                    }.filter { !it.complete && it.date == constant.selectTime }) { index, item ->

                        NoteCard(
                            modifier = Modifier.padding(12.dp, 0.dp),
                            item = item,
                            viewModel = viewModel,
                            onClick = {
                                onClick(it)
                            }

                        )
                    }

                    //completed
                    stickyHeader {
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .background(ColorBACK)
                        ) {
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "completed",
                                fontFamily = FontFamily(Font(com.myexample.R.font.rubik_bold)),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                            )
                        }

                    }


                    itemsIndexed(state.sortedBy {
                        it.status
                    }.filter { it.complete && it.date == constant.selectTime }) { index, item ->
                        NoteCard(
                            modifier = Modifier.padding(12.dp, 0.dp),
                            item = item,
                            viewModel = viewModel,
                            onClick = {
                                onClick(it)
                            }

                        )

                    }
                }
            }

            //select time
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
