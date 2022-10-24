package com.myexample.presentation.Diary

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.myexample.R
import com.myexample.presentation.ui.theme.*


/*
  **Created by 24606 at 1:15 2022.
*/

enum class Mood(@DrawableRes val icon: Int, val color: Color, val title: String, val value: Int) {
    AWESOME(R.drawable.ic_very_happy, Green, awesome, 5),
    GOOD(R.drawable.ic_happy, Blue, good, 4),
    OKAY(R.drawable.ic_ok_face, Purple, okay, 3),
    BAD(R.drawable.ic_sad, Orange, bad, 2),
    TERRIBLE(R.drawable.ic_very_sad, Red, terrible, 1)
}

const val awesome = "awesome"
const val good = "good"
const val okay = "okay"
const val bad = "bad"
const val terrible = "terrible"

