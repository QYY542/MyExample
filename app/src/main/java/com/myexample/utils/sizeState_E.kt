package com.myexample.utils

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
  **Created by 24606 at 17:46 2022.
*/

sealed class sizeState_E(val size: Dp) {
    //    object Small : BoxState(200.dp)
//    object Large : BoxState(250.dp)
    object Small : sizeState_E(80.dp)
    object Large : sizeState_E(90.dp)

    operator fun not() = when (this) {
        Small -> Large
        Large -> Small
    }
}