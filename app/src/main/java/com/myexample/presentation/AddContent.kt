package com.myexample.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.myexample.MainViewModel

import com.myexample.presentation.Diary.DiaryDetail
import com.myexample.presentation.Diary.DiaryViewModel
import com.myexample.presentation.Tasks.TaskDetail
import com.myexample.presentation.Tasks.TaskViewModel
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddContent(
    sheetState: ModalBottomSheetState,
    mainViewModel: MainViewModel,
    taskViewModel: TaskViewModel,
    diaryViewModel: DiaryViewModel,
    navController: NavController
) {

    when (mainViewModel.navController_Number.value) {
        0 -> {
            TaskDetail(sheetState, taskViewModel)
        }
        1 -> {
            TaskDetail(sheetState, taskViewModel)
        }
        2 -> {
            TaskDetail(sheetState, taskViewModel)
        }
        3 -> {
            DiaryDetail(
                sheetState = sheetState,
                diaryViewModel = diaryViewModel
            )
        }
    }
}


