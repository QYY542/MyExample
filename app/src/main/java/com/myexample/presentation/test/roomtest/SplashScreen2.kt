package com.myexample.presentation.test.roomtest

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.myexample.R
import com.myexample.presentation.Note.NoteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 0:04 2022.
*/

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun SplashScreen2(
    navController: NavController,
    viewModel: NoteViewModel
) {
//    viewModel.navController_Number.value = 0
    var coroutineScope = rememberCoroutineScope()

    var replay by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableStateOf(6f)
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_2))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = speed,
        iterations = 1,
        cancellationBehavior = LottieCancellationBehavior.OnIterationFinish,
        isPlaying = replay,
        restartOnPlay = false
    )
    var flag = true
    if (flag) {
        Box(Modifier.fillMaxSize()) {
            LottieAnimation(
                composition = composition,
                progress = progress,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
    }


    coroutineScope.launch {
        delay(1000)
        flag = false
        replay = false
        delay(1000)
        navController.navigate("note_screen")
        navController.enableOnBackPressed(false)
    }
}

