package com.myexample.presentation.diary

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import com.myexample.R
import com.myexample.presentation.ui.theme.Orange
import com.myexample.presentation.ui.theme.Purple

/*
  **Created by 24606 at 1:15 2022.
*/

enum class Mood(@DrawableRes val icon: Int, val color: Color, val title: String, val value: Int) {
    AWESOME(R.drawable.ic_very_happy, Green, awesome, 5),
    GOOD(R.drawable.ic_happy, Blue, good, 4),
    OKAY(R.drawable.ic_ok_face, Purple, okay, 3),
    BAD(R.drawable.ic_sad, Orange, bad, 2),
    TERRIBLE(R.drawable.ic_very_sad, Color.Red, terrible, 1)
}

const val awesome = "awesome"
const val good = "good"
const val okay = "okay"
const val bad = "bad"
const val terrible = "terrible"

