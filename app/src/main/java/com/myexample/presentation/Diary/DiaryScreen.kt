package com.myexample.presentation.Diary

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.myexample.MainViewModel
import com.myexample.R
import com.myexample.data.MyDiary.MyDiary

/*
  **Created by 24606 at 23:37 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DiaryScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    sheetState: ModalBottomSheetState,
    diaryViewModel: DiaryViewModel,
    onClick: (item: MyDiary) -> Unit
) {
    mainViewModel.setNavControllerNumber(3)
    navController.enableOnBackPressed(false)
    DiaryHome(sheetState, diaryViewModel, onClick = {
        onClick(it)
    })
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@Composable
fun DiaryHome(
    sheetState: ModalBottomSheetState,
    diaryViewModel: DiaryViewModel,
    onClick: (item: MyDiary) -> Unit
) {
    val state by diaryViewModel.state.collectAsState()
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Diary",
                            fontFamily = FontFamily(Font(R.font.rubik_bold)),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                backgroundColor = Color.White

            )
        }
    ) {
        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(60.dp))
            val listState = rememberLazyListState()

            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    itemsIndexed(state.sortedBy { it.date }.reversed()) { index, item ->
                        ///
                        DiaryCard(
                            Modifier.animateItemPlacement(),
                            item,
                            diaryViewModel,
                            onClick = {
                                onClick(it)
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun DiaryInfo(
    diaryViewModel: DiaryViewModel = hiltViewModel()
) {
    BoxText("DiaryInfo")
}

@Composable
fun BoxText(text: String) {
    Box(Modifier.fillMaxSize()) {
        Text(text = text, modifier = Modifier.align(Alignment.Center))
    }
}

