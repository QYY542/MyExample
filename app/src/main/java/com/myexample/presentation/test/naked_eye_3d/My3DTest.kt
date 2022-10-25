package com.myexample.presentation.test.naked_eye_3d

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.hardware.SensorManager.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getSystemService
import com.myexample.R
import com.myexample.presentation.Tasks.TaskViewModel

/*
  **Created by 24606 at 16:59 2022.
*/
const val TAG = "MainActivity"

@Composable
fun My3DTest(
    viewModel: TaskViewModel
) {

    val imageBack = ImageBitmap.imageResource(id = R.drawable.icon_three_bg)
    val imageMid = ImageBitmap.imageResource(id = R.drawable.icon_char2)
    val imageFore = ImageBitmap.imageResource(id = R.drawable.icon_three_small)

    var xDistance by remember { mutableStateOf(0f) }
    var yDistance by remember { mutableStateOf(0f) }

    val context = LocalContext.current
    val sensorManager: SensorManager? = getSystemService(context, SensorManager::class.java)
    val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    var speedY by remember { mutableStateOf(0f) }
    var speedX by remember { mutableStateOf(0f) }
    var speedZ by remember { mutableStateOf(0f) }
    var angularX by remember { mutableStateOf(0f) }
    var angularY by remember { mutableStateOf(0f) }
    var angularZ by remember { mutableStateOf(0f) }
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }
    var z by remember { mutableStateOf(0f) }

    var maxOffset by remember { mutableStateOf(150f) }
    var dT by remember { mutableStateOf(2f) }
    var mMaxAnular by remember { mutableStateOf(150f) }

    var w = 0
    var h = 0

    sensorManager?.registerListener(object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent?) {
            //Y轴角速度
            speedY = event?.values?.get(1)!!
            //X轴角速度
            speedX = event?.values?.get(0)!!
            //Z轴角速度
            speedZ = event?.values?.get(2)!!

            // 将手机在各个轴上的旋转角度相加
            angularX += (event.values[0] * dT).toLong()
            angularY += (event.values[1] * dT).toLong()
            angularZ += (event.values[2] * dT).toLong()

            //设置x轴y轴最大边界值，
            if (angularY > mMaxAnular) {
                angularY = mMaxAnular.toFloat()
            } else if (angularY < -mMaxAnular) {
                angularY = -mMaxAnular.toFloat()
            }

            if (angularX > mMaxAnular) {
                angularX = mMaxAnular.toFloat()
            } else if (angularX < -mMaxAnular) {
                angularX = -mMaxAnular.toFloat()
            }

            val xRadio: Float = (angularY / mMaxAnular).toFloat()
            val yRadio: Float = (angularX / mMaxAnular).toFloat()


            x = Math.abs(event.values[0])
            y = Math.abs(event.values[1])
            z = Math.abs(event.values[2])
            xDistance = xRadio * maxOffset
            yDistance = yRadio * maxOffset

        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

        }
    }, sensor, SENSOR_DELAY_GAME)

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .size(10.dp)
            .scale(1.3f)
    ) {
        h = size.height.toInt()
        w = size.width.toInt()

        translate(-xDistance, -yDistance) {
            drawImage(imageBack, dstSize = IntSize(w, h))
        }

        drawImage(imageMid, dstSize = IntSize(w, h))

        translate(xDistance, yDistance) {
            drawImage(imageFore, dstSize = IntSize(w, h))
        }

    }


}




