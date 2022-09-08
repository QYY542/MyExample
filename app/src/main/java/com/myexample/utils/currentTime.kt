package com.myexample.utils

import com.myexample.utils.currentTime.calendar
import java.lang.Math.abs
import java.sql.Time
import java.util.*

/*
  **Created by 24606 at 15:04 2022.
*/

object currentTime {

    var calendar: Calendar = Calendar.getInstance()

    fun year(): Int {

        return calendar.get(Calendar.YEAR)
    }

    fun month(): Int {
        return calendar.get(Calendar.MONTH) + 1
    }

    fun day(): Int {
        return calendar.get(Calendar.DAY_OF_MONTH)
    }

    fun hour(): Int {
        return calendar.get(Calendar.HOUR_OF_DAY)
    }

    fun minute(): Int {
        return calendar.get(Calendar.MINUTE)
    }

    fun second(): Int {
        return calendar.get(Calendar.SECOND)
    }

    fun otherday(days: Int): String {
        calendar.add(Calendar.DATE, days)
        val time =
            "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${
                calendar.get(
                    Calendar.DAY_OF_MONTH
                )
            }"
        calendar.add(Calendar.DATE, -days)
        return time
    }

    fun formatTime(): String {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val time =
            "${year}-${month}-${day}"
        return time
    }

    fun formatTimeDetail():String{
        "9月9日，2022 15:20"
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val time =
            "${month}月${day}日,${year} ${hour}:${minute}"
        return time
    }


    fun daysBetween(date_curr: Date, date_other: Date): Int {
        val cal = Calendar.getInstance();
        cal.setTime(date_curr);
        val time1 = cal.timeInMillis;
        cal.setTime(date_other);
        val time2 = cal.timeInMillis;
        val between_days = (time2 - time1) / (1000 * 3600 * 24);

        return between_days.toInt();
    }

}