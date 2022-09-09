package com.myexample.presentation

import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.myexample.data.MyData.MyData
import com.myexample.presentation.diary.DiaryDetail
import com.myexample.presentation.diary.DiaryScreen
import com.myexample.presentation.diary.DirayViewModel
import com.myexample.presentation.note.NoteScreen
import com.myexample.presentation.splash.SplashScreen
import com.myexample.presentation.ttarget.TargetScreen
import com.myexample.presentation.note.MyViewModel

/*
  **Created by 24606 at 23:39 2022.
*/

@Composable
fun Nav(
    navController: NavController,
    viewModel: MyViewModel,
    onClick: (item: MyData) -> Unit
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
            })
        }
        composable("diary_screen") {
            DiaryScreen(navController, viewModel)
        }
        composable("target_screen") {
            TargetScreen(navController, viewModel)
        }
        composable(
            route = "diary_detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val diaryViewModel = hiltViewModel<DirayViewModel>()
            var id by remember {

                mutableStateOf(1)
            }
            backStackEntry.arguments?.getInt("id")?.let { id = it }
            DiaryDetail(id = id, navController = navController)
        }

    }
}
