package com.myexample.presentation.Note

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.myexample.R
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

fun Status.toPriority(): Priority {
    when (title) {
        incompleted_red -> {
            return Priority.HIGH
        }
        incompleted_orange -> {
            return Priority.MEDIUM
        }
        incompeted_green -> {
            return Priority.LOW
        }
        completed -> {
            return Priority.LOW
        }
    }
    return Priority.LOW
}

const val incompleted_red = "incompleted_red"
const val incompleted_orange = "incompleted_orange"
const val incompeted_green = "incompleted_green"
const val completed = "completed"

