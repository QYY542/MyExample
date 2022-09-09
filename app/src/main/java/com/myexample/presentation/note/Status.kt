package com.myexample.presentation.note

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import com.myexample.R
import com.myexample.presentation.diary.awesome
import com.myexample.presentation.diary.bad
import com.myexample.presentation.diary.good
import com.myexample.presentation.diary.okay
import com.myexample.presentation.ui.theme.*


/*
  **Created by 24606 at 1:15 2022.
*/

enum class Status(@DrawableRes val icon: Int, val color: Color, val title: String, val value: Int) {
    INCOMPLETED_RED(R.drawable.ic_imcompleted, Red, incompleted_red, 4),
    INCOMPLETED_ORANGE(R.drawable.ic_imcompleted, Orange, incompleted_orange, 3),
    INCOMPLETED_GREEN(R.drawable.ic_imcompleted, Green, incompeted_green, 2),
    COMPLETED(R.drawable.ic_completed, Gray, completed, 1),
}

const val incompleted_red = "incompleted_red"
const val incompleted_orange = "incompleted_orange"
const val incompeted_green = "incompleted_green"
const val completed = "completed"

