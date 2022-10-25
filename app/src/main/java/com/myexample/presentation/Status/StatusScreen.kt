package com.myexample.presentation.Status

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
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
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.VerticalPager
import com.google.accompanist.pager.rememberPagerState
import com.myexample.MainViewModel
import com.myexample.R
import com.myexample.data.MyTarget.MyTarget
import com.myexample.presentation.target.StatusViewModel
import com.myexample.presentation.ui.theme.ColorBACK

/*
  **Created by 24606 at 23:39 2022.
*/

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun StatusScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    statusViewModel: StatusViewModel
) {
    mainViewModel.setNavControllerNumber(2)
    navController.enableOnBackPressed(false)

    val state = statusViewModel.state.collectAsState()


    Scaffold(
        backgroundColor = ColorBACK,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Status",
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
//        Column(Modifier.fillMaxSize()) {
//            PersonalStatusList(state, targetViewModel)
//            AnnuallyStatusList(state, targetViewModel)
//        }
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "开发中...", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AnnuallyStatusList(
    state: State<List<MyTarget>>,
    statusViewModel: StatusViewModel = hiltViewModel()
) {

    var id by remember {
        mutableStateOf(1)
    }
    var personNum by remember {
        mutableStateOf(1)
    }
    var gNum by remember {
        mutableStateOf(1)
    }
    var iss by remember {
        mutableStateOf(1)
    }
    var item by remember {
        mutableStateOf(MyTarget())
    }

//    LaunchedEffect(key1 = state.value) {
//        state.value.forEach {
//            if (it.id == 0) {
//                id = it.id
//                personNum = it.personNum
//                gNum = it.gNum
//            }
//        }
//    }
    LaunchedEffect(key1 = Unit) {
        id = 1
        personNum = item.personNum
        gNum = item.gNum

    }


    val pagerState = rememberPagerState()
    var pageCount by remember {
        mutableStateOf(5)
    }
    var lastPage by remember {
        mutableStateOf(pagerState.currentPage)
    }
    LaunchedEffect(key1 = pagerState.currentPage) {
        if (lastPage > pagerState.currentPage) {
            pageCount--
        } else {
            pageCount++
        }
        lastPage = pagerState.currentPage
    }
    VerticalPager(
        modifier = Modifier.fillMaxWidth(),
        count = pageCount,
        state = pagerState,
    ) { page ->
        Column(Modifier.fillMaxWidth()) {
            Card(
                Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                backgroundColor = Color.Gray
            ) {
                androidx.compose.material.Text(text = "Page: $pageCount")
            }
            Card(
                Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clickable {
                        pageCount++
                    },
                backgroundColor = Color.Red
            ) {
                Text(text = "$id = $personNum = $gNum = $iss")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PersonalStatusList(
    state: State<List<MyTarget>>,
    statusViewModel: StatusViewModel = hiltViewModel()
) {
    val pagerState = rememberPagerState()
    var pageCount by remember {
        mutableStateOf(5)
    }
    var lastPage by remember {
        mutableStateOf(pagerState.currentPage)
    }
    LaunchedEffect(key1 = pagerState.currentPage) {
        if (lastPage > pagerState.currentPage) {
            pageCount--
        } else {
            pageCount++
        }
        lastPage = pagerState.currentPage
    }
    HorizontalPager(
        modifier = Modifier.fillMaxWidth(),
        count = pageCount,
        state = pagerState,
    ) { page ->
        Row(Modifier.fillMaxWidth()) {
            Card(
                Modifier
                    .height(250.dp)
                    .fillMaxWidth(0.8f),
                backgroundColor = Color.Gray
            ) {
                androidx.compose.material.Text(text = "Page: $pageCount")
            }
            Card(
                Modifier
                    .height(250.dp)
                    .fillMaxWidth()
                    .clickable {
                        pageCount++
                    },
                backgroundColor = Color.Red
            ) {

            }
        }
    }
}


