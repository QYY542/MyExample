package com.myexample.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

/*
  **Created by 24606 at 14:59 2022.
*/

@Composable
fun vibrator_E() {

}

fun vibrate(context: Context) {
    var vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val vibEff: VibrationEffect
    // Android Q and above have some predefined vibrating patterns
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        vibEff = VibrationEffect.createPredefined(VibrationEffect.EFFECT_DOUBLE_CLICK)
        vibrator.cancel()
        vibrator.vibrate(vibEff)
    } else {
        vibrator.vibrate(500)
    }
}

fun vibrate_2(context: Context) {
    var vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    val vibEff: VibrationEffect
    // Android Q and above have some predefined vibrating patterns
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        vibEff = VibrationEffect.createPredefined(VibrationEffect.EFFECT_HEAVY_CLICK)
        vibrator.cancel()
        vibrator.vibrate(vibEff)
    } else {
        vibrator.vibrate(500)
    }
}

@Preview
@Composable
fun vibrator_EPreview() {
    vibrator_E()
}