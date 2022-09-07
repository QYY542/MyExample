package com.myexample.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.myexample.viewModel.MyViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 0:04 2022.
*/

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: MyViewModel
) {
//    viewModel.navController_Number.value = 0
    viewModel.setNavControllerNumber(0)
    var coroutineScope = rememberCoroutineScope()

    Box(Modifier.fillMaxSize()) {
        Text(text = "Hello", modifier = Modifier.align(Alignment.Center))
    }

    coroutineScope.launch {
        delay(1000)
        navController.navigate("note_screen")
    }
}

