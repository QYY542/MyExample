package com.myexample.presentation.test.roomtest

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.diary.DiaryScreen
import com.myexample.presentation.diary.DirayViewModel
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.note.NoteScreen
import com.myexample.presentation.splash.SplashScreen
import com.myexample.presentation.ttarget.TargetScreen

/*
  **Created by 24606 at 0:23 2022.
*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NavTest(
    navController: NavController,
    viewModel: MyViewModel
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