package com.myexample.presentation.note

import android.annotation.SuppressLint
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.myexample.data.MyData.MyData

/*
  **Created by 24606 at 23:37 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun NoteScreen(
    navController: NavController,
    viewModel: MyViewModel,
    onClick: (item: MyData) -> Unit,
    sheetState: ModalBottomSheetState
) {
    viewModel.setNavControllerNumber(1)
    navController.enableOnBackPressed(false)
    NoteHome(viewModel, onClick = {
        onClick(it)
    }, sheetState)
}


