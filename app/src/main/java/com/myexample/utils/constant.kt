package com.myexample.utils

object constant {
    val appname = "ToDo"
    val currTime = currentTime.formatTime()
    var selectTime = currentTime.otherday(0)
    var selectId: Int? = null
    var onAddButton: Boolean = true
    var inSheet: Boolean = false
    var isChange: Boolean = false
}