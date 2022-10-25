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

import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.Diary.DiaryDetail
import com.myexample.presentation.Diary.DirayViewModel
import com.myexample.presentation.Note.NoteAddContent
import com.myexample.presentation.Note.NoteViewModel
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddContent(
    sheetState: ModalBottomSheetState,
    mainViewModel: MainViewModel,
    noteViewModel: NoteViewModel,
    diaryViewModel: DirayViewModel,
    navController: NavController
) {

    when (mainViewModel.navController_Number.value) {
        0 -> {
            NoteAddContent(sheetState, noteViewModel)
        }
        1 -> {
            NoteAddContent(sheetState, noteViewModel)
        }
        2 -> {
            NoteAddContent(sheetState, noteViewModel)
        }
        3 -> {
            DiaryDetail(
                sheetState = sheetState,
                diaryViewModel = diaryViewModel
            )
        }
    }
}


