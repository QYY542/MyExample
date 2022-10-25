package com.myexample.presentation.test.roomtest

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myexample.presentation.Tasks.TaskViewModel

/*
  **Created by 24606 at 0:23 2022.
*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavTest(
    navController: NavController,
    viewModel: TaskViewModel
//    dirayViewModel: DirayViewModel,
//    sheetState: ModalBottomSheetState,
//    onClick: (item: MyData) -> Unit,
//    onClickDiary: (item: MyDiary) -> Unit
) {
    NavHost(
        navController = navController as NavHostController,
        startDestination = "note_screen",
    ) {
        composable("splash_screen") {
            SplashScreen2(navController, viewModel)
        }
        composable("note_screen") {
            MyRoomTest()
        }
        composable("diary_screen") {
            MyDiray()
        }
        composable("target_screen") {
            MyTarget()
        }

    }
}