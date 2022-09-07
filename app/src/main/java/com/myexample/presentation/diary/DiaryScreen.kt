package com.myexample.presentation.diary

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.myexample.viewModel.MyViewModel

/*
  **Created by 24606 at 23:37 2022.
*/

@Composable
fun DiaryScreen(
    navController: NavController,
    viewModel: MyViewModel
) {
    viewModel.setNavControllerNumber(3)
    Text(text = "Diary")
}

