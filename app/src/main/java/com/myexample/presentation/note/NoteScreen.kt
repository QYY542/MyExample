package com.myexample.presentation.note

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

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

    //Pager
    var pagerState = rememberPagerState(0)
    HorizontalPager(
        state = pagerState,
        count = 2
    ) { page ->
        when (page) {
            0 -> NoteHome(viewModel)
            1 -> NoteHome_2(viewModel)
        }
    }
}


