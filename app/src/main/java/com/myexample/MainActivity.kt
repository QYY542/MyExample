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
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.test.TestHome
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.view.WindowCompat
import androidx.room.util.TableInfo
import com.myexample.presentation.diary.DirayViewModel
import com.myexample.utils.constant.inSheet
import com.myexample.utils.constant.isChange

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

                        val viewModel: MyViewModel by viewModels()
                        val diaryViewModel: DirayViewModel by viewModels()

                        Column(Modifier.fillMaxSize()) {
                            Spacer(modifier = Modifier.height(30.dp))
                            MyScreen(viewModel, diaryViewModel)
//                            TestHome(viewModel)
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


