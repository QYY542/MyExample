package com.myexample.presentation.splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.myexample.R
import com.myexample.presentation.note.MyViewModel
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

    var replay by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableStateOf(2f)
    }
    var alpha by remember {
        mutableStateOf(1f)
    }
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_2))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        speed = speed,
        iterations = 1,
        cancellationBehavior = LottieCancellationBehavior.Immediately,
        isPlaying = replay,
        restartOnPlay = false
    )
    Box(Modifier.fillMaxSize()) {
//        Text(text = "Hello", modifier = Modifier.align(Alignment.Center))
        LottieAnimation(
            composition = composition,
            progress = progress,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .alpha(alpha)
        )
    }

    coroutineScope.launch {
        delay(1000)
        replay = false
        alpha = 0f
//        delay(20)
        navController.navigate("note_screen")
        navController.enableOnBackPressed(false)
    }
}

