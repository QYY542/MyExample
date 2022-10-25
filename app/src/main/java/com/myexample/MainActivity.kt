package com.myexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.myexample.presentation.MyScreen
import com.myexample.presentation.ui.theme.MyExampleTheme
import com.myexample.presentation.Tasks.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.WindowCompat
import com.myexample.presentation.Diary.DiaryViewModel
import com.myexample.presentation.target.StatusViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyExampleTheme {
                ProvideWindowInsets {
                    // 状态栏改为透明
                    rememberSystemUiController().setStatusBarColor(
                        Color.Transparent,
                        darkIcons = androidx.compose.material.MaterialTheme.colors.isLight
                    )
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        val mainViewModel: MainViewModel by viewModels()

                        val taskViewModel: TaskViewModel by viewModels()
                        val statusViewModel: StatusViewModel by viewModels()
                        val diaryViewModel: DiaryViewModel by viewModels()


                        Column(Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.height(30.dp))
                            MyScreen(mainViewModel, taskViewModel, statusViewModel, diaryViewModel)
//                            TestHome(noteViewModel)
                        }

                    }
                }
            }
        }
    }

//    override fun onBackPressed() {
//        if (!inSheet) {
//            isChange = false
//            super.onBackPressed()
//        } else {
//            inSheet = false
//            isChange = true
//            println("back")
//        }
//
//    }
}


