package com.myexample.presentation.Splash

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.*
import com.myexample.MainViewModel
import com.myexample.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 0:04 2022.
*/

@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun SplashScreen(
    navController: NavController,
    mainViewModel: MainViewModel
) {
//    viewModel.navController_Number.value = 0
    mainViewModel.setNavControllerNumber(0)
    var coroutineScope = rememberCoroutineScope()

    var replay by remember {
        mutableStateOf(true)
    }
    var speed by remember {
        mutableStateOf(6f)
    }
    var alpha by remember {
        mutableStateOf(true)
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
    Box(Modifier.fillMaxSize()) {
        Text(
            text = "Hello ToDo",
            fontFamily = FontFamily(Font(R.font.rubik_bold)),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
//            LottieAnimation(
//                composition = composition,
//                progress = progress,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.Center)
//            )
    }

    coroutineScope.launch {
        delay(1000)
        replay = false
        alpha = false
        navController.navigate("note_screen")
        navController.enableOnBackPressed(false)
    }
}

