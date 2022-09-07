package com.myexample.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myexample.presentation.diary.DiaryScreen
import com.myexample.presentation.note.NoteScreen
import com.myexample.presentation.splash.SplashScreen
import com.myexample.presentation.target.TargetScreen
import com.myexample.presentation.note.MyViewModel

/*
  **Created by 24606 at 23:39 2022.
*/

@Composable
fun Nav(
    navController: NavController,
    viewModel: MyViewModel
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "splash_screen",
    ) {
        composable("splash_screen") {
            SplashScreen(navController, viewModel)
        }
        composable("note_screen") {
            NoteScreen(navController, viewModel)
        }
        composable("diary_screen") {
            DiaryScreen(navController, viewModel)
        }
        composable("target_screen") {
            TargetScreen(navController, viewModel)
        }
    }
}
