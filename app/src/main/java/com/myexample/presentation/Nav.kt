package com.myexample.presentation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.*
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myexample.MainViewModel
import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.Diary.DiaryScreen
import com.myexample.presentation.Diary.DiaryViewModel
import com.myexample.presentation.Tasks.TaskScreen
import com.myexample.presentation.Splash.SplashScreen
import com.myexample.presentation.Status.StatusScreen
import com.myexample.presentation.Tasks.TaskViewModel
import com.myexample.presentation.target.StatusViewModel

/*
  **Created by 24606 at 23:39 2022.
*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Nav(
    navController: NavController,
    mainViewModel: MainViewModel,
    taskViewModel: TaskViewModel,
    statusViewModel: StatusViewModel,
    diaryViewModel: DiaryViewModel,
    sheetState: ModalBottomSheetState,
    onClickNote: (item: MyData) -> Unit,
    onClickDiary: (item: MyDiary) -> Unit
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "splash_screen",
    ) {

        composable("splash_screen") {
            SplashScreen(
                navController,
                mainViewModel
            )
        }

        composable("note_screen") {
            TaskScreen(
                navController,
                mainViewModel,
                taskViewModel,
                onClick = {
                    onClickNote(it)
                }
            )
        }

        composable("target_screen") {
            StatusScreen(
                navController,
                mainViewModel,
                statusViewModel
            )
        }

        composable("diary_screen") {
            DiaryScreen(
                navController,
                mainViewModel,
                sheetState,
                diaryViewModel,
                onClick = {
                    onClickDiary(it)
                })
        }

    }
}
