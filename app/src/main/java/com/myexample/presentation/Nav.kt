package com.myexample.presentation

import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.diary.DiaryDetail
import com.myexample.presentation.diary.DiaryScreen
import com.myexample.presentation.diary.DirayViewModel
import com.myexample.presentation.note.NoteScreen
import com.myexample.presentation.splash.SplashScreen
import com.myexample.presentation.ttarget.TargetScreen
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.test.roomtest.SplashScreen2

/*
  **Created by 24606 at 23:39 2022.
*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Nav(
    navController: NavController,
    viewModel: MyViewModel,
    dirayViewModel: DirayViewModel,
    sheetState: ModalBottomSheetState,
    onClick: (item: MyData) -> Unit,
    onClickDiary: (item: MyDiary) -> Unit
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "splash_screen",
    ) {
        composable("splash_screen") {
            SplashScreen(navController, viewModel)
        }
        composable("note_screen") {
            NoteScreen(navController, viewModel, onClick = {
                onClick(it)
            }, sheetState)
        }
        composable("diary_screen") {
            DiaryScreen(navController, sheetState, viewModel, dirayViewModel, onClick = {
                onClickDiary(it)
            })
        }
        composable("target_screen") {
            TargetScreen(navController, viewModel)
        }

    }
}
