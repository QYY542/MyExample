package com.myexample.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.mhss.app.mybrain.presentation.tasks.AddTaskBottomSheetContent
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.diary.DirayViewModel
import com.myexample.utils.sizeState_E
import com.myexample.utils.vibrate
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.ui.theme.ColorBACK
import com.myexample.utils.constant
import com.myexample.utils.constant.onAddButtonChange
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 12:58 2022.
*/
//方位
val localX = 120.dp
val localY = 60.dp

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalComposeUiApi::class
)
@Composable
fun MyScreen(
    viewModel: MyViewModel,
    diaryViewModel: DirayViewModel
) {
    //基本
    val context = LocalContext.current
    val navController = rememberNavController()
    val navController_Number by viewModel.navController_Number
    var coroutineScope = rememberCoroutineScope()
    val kc = LocalSoftwareKeyboardController.current
    //语音动画
//    var alpha by remember {
//        mutableStateOf(0F)
//    }
//    var replay by remember {
//        mutableStateOf(false)
//    }
//    var speed by remember {
//        mutableStateOf(2f)
//    }
//    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.sun))
//    val progress by animateLottieCompositionAsState(
//        composition = composition,
//        speed = speed,
//        iterations = LottieConstants.IterateForever,
//        cancellationBehavior = LottieCancellationBehavior.Immediately,
//        isPlaying = replay,
//        restartOnPlay = false
//    )
    //长按放大动画
    var boxState: sizeState_E by remember {
        mutableStateOf(sizeState_E.Small)
    }
    val animSize by animateDpAsState(
        targetValue = boxState.size, animationSpec = tween(
            durationMillis = 200,
            delayMillis = 0,
            easing = FastOutSlowInEasing
        )
    )
    //三个小图标移动动画
    var offState by remember {
//        mutableStateOf(Offset(screenWidth / 2, screenHeight - 150))
        mutableStateOf(IntOffset(0, 0))
    }
    var dpOffState_x_l by remember {
        mutableStateOf(localX)
    }
    val animdpOff_x_l by animateDpAsState(
        targetValue = dpOffState_x_l, animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    var dpOffState_x_m by remember {
        mutableStateOf(localX)
    }
    val animdpOff_x_m by animateDpAsState(
        targetValue = dpOffState_x_m, animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    var dpOffState_y_l by remember {
        mutableStateOf(0.dp)
    }
    val animdpOff_y_l by animateDpAsState(
        targetValue = dpOffState_y_l, animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    var dpOffState_y_m by remember {
        mutableStateOf(0.dp)
    }
    val animdpOff_y_m by animateDpAsState(
        targetValue = dpOffState_y_m, animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    //三个小图标尺寸动画
    var touch_x by remember { mutableStateOf(0f) }
    var touch_y by remember { mutableStateOf(0f) }
    var dpState_1 by remember {
        mutableStateOf(55.dp)
    }
    val animPD_1 by animateDpAsState(
        targetValue = dpState_1, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    var dpState_2 by remember {
        mutableStateOf(55.dp)
    }
    val animPD_2 by animateDpAsState(
        targetValue = dpState_2, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    var dpState_3 by remember {
        mutableStateOf(55.dp)
    }
    val animPD_3 by animateDpAsState(
        targetValue = dpState_3, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )
    //振动
    LaunchedEffect(key1 = dpState_1 + boxState.size, key2 = dpState_2, key3 = dpState_3) {
        vibrate(context = context)
    }
    //添加日程状态
    var ifAddNewMission by remember {
        mutableStateOf(false)
    }
    val sheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    var item by remember {
        mutableStateOf(MyData())
    }

    var itemDiary by remember {
        mutableStateOf(MyDiary())
    }

    var inSheet by remember {
        mutableStateOf(false)
    }
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = sheetState.isVisible) {
        if (!sheetState.isVisible) {
            inSheet = false
            kc?.hide()
            focusManager.clearFocus()
        } else {
            inSheet = true
        }
    }

    BackHandler(enabled = inSheet) {
        coroutineScope.launch {
            sheetState.animateTo(ModalBottomSheetValue.Hidden)
            sheetState.hide()
        }
        inSheet = false
    }

//    LaunchedEffect(key1 = inSheet) {
//        if (!inSheet) {
////            sheetState.animateTo(ModalBottomSheetValue.Hidden)
////            sheetState.hide()
////            isChange = false
//            println("$inSheet")
//        } else {
//            println("$inSheet")
//        }
//        println("====================================")
//    }
    ////////////////////////////////////
    Box() {
        /**
         * 右下角的按钮
         */
        Scaffold(
            backgroundColor = ColorBACK,
            topBar = {
//            Row(horizontalArrangement=Arrangement.SpaceBetween, modifier = Modifier.height(30.dp)) {
//                Spacer(modifier = Modifier.width(20.dp))
//                Column(verticalArrangement = Arrangement.Center, modifier = Modifier.fillMaxSize()) {
//                    Text(text = topBarItems[navController_Number-1], fontSize = 4.em,fontWeight = FontWeight.Bold)
//                }
//
//            }

            },
            floatingActionButton = {

                if (navController_Number != 0) {
                    //left
                    FloatingActionButton(
                        backgroundColor =
                        if (dpState_1 == 55.dp) Color(156, 174, 218)
                        else Color(114, 137, 196),
                        onClick = { /* ... */ },
                        modifier = Modifier
                            .size(animSize)
                            .offset(animdpOff_x_l, -localY)
                            .padding(animPD_1 - 40.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_task),
                            contentDescription = "Camera",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    //mid
                    FloatingActionButton(
                        backgroundColor =
                        if (dpState_2 == 55.dp) Color(156, 174, 218)
                        else Color(114, 137, 196),
                        onClick = { /* ... */ },
                        modifier = Modifier
                            .size(animSize)
                            .offset(localX, -animdpOff_y_l - localY)
                            .padding(animPD_2 - 40.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_diary),
                            contentDescription = "Camera",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    //right
                    FloatingActionButton(
                        backgroundColor =
                        if (dpState_3 == 55.dp) Color(156, 174, 218)
                        else Color(114, 137, 196),
                        onClick = { /* ... */ },
                        modifier = Modifier
                            .size(animSize)
                            .offset(animdpOff_x_m, -animdpOff_y_m - localY)
                            .padding(animPD_3 - 40.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_target),
                            contentDescription = "Camera",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    //Big
                    FloatingActionButton(
                        backgroundColor = Color(114, 137, 196),
                        onClick = {
                            diaryViewModel.onRefresh()
//                            ifAddNewMission = !ifAddNewMission
                            item = MyData()
                            constant.onAddButton = true
                            onAddButtonChange++

                            coroutineScope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.animateTo(ModalBottomSheetValue.Hidden)
                                } else {
                                    sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                }

                            }
                        },
                        modifier = Modifier
                            .size(animSize)
                            .offset(localX, -localY)
                            .pointerInput(Unit) {
                                detectDragGesturesAfterLongPress(
                                    onDrag = { change, dragAmount ->
                                        change.consumeAllChanges()
                                        coroutineScope.launch {
                                            touch_x += dragAmount.x
                                            touch_y += dragAmount.y

                                            //取消
                                            if ((Math.abs(touch_x) < 100 && Math.abs(touch_y) < 100) || (Math.abs(
                                                    touch_y
                                                ) > 150)
                                            ) {
                                                dpState_1 = 55.dp
                                                dpState_2 = 55.dp
                                                dpState_3 = 55.dp
                                            }

                                            if ((touch_y > 2.5 * touch_x) && (touch_y < 0.4 * touch_x)) {
                                                //mid
                                                if (touch_x < -80 && touch_y < -80) {
                                                    dpState_1 = 55.dp
                                                    dpState_2 = 55.dp
                                                    dpState_3 = 50.dp
                                                }

                                            } else if ((touch_y < 2.5 * touch_x) && (touch_y < -2.5 * touch_x)) {
                                                //right
                                                if (touch_y < -100) {
                                                    dpState_1 = 55.dp
                                                    dpState_2 = 50.dp
                                                    dpState_3 = 55.dp
                                                }

                                            } else if ((touch_y < -0.4 * touch_x) && (touch_y > 0.4 * touch_x)) {
                                                //left
                                                if (touch_x < -100) {
                                                    dpState_1 = 50.dp
                                                    dpState_2 = 55.dp
                                                    dpState_3 = 55.dp
                                                }


                                            }
                                        }
                                    },
                                    onDragStart = {
                                        ifAddNewMission = false
                                        //动画
                                        coroutineScope.launch {
                                            dpOffState_x_l = 30.dp
                                            dpOffState_y_l = 90.dp
                                            dpOffState_x_m = 50.dp
                                            dpOffState_y_m = 70.dp
                                            boxState = !boxState
//                                    replay = true
                                            offState = IntOffset(offState.x - 250, offState.y - 250)
                                        }
                                        //关闭添加窗口
                                        coroutineScope.launch {
                                            sheetState.animateTo(ModalBottomSheetValue.Hidden)
                                            sheetState.hide()
                                        }


                                    },
                                    onDragEnd = {
                                        Log.d("=====", "${navController_Number}")
                                        ifAddNewMission = false

                                        //动画
                                        coroutineScope.launch {
                                            dpOffState_x_l = localX
                                            dpOffState_y_l = 0.dp
                                            dpOffState_x_m = localX
                                            dpOffState_y_m = 0.dp
                                            boxState = !boxState
                                            offState = IntOffset(offState.x + 250, offState.y + 250)
                                            touch_x = 0f
                                            touch_y = 0f
                                            dpState_1 = 55.dp
                                            dpState_2 = 55.dp
                                            dpState_3 = 55.dp
                                        }

                                        //navigation
                                        if (dpState_1 == 50.dp && navController_Number != 1) {
                                            navController.navigate("note_screen")
//                                            viewModel.setNavControllerNumber(1)
                                        } else if (dpState_2 == 50.dp && navController_Number != 3) {
                                            navController.navigate("diary_screen")
//                                            viewModel.setNavControllerNumber(2)
                                        } else if (dpState_3 == 50.dp && navController_Number != 2) {
                                            navController.navigate("target_screen")
//                                            viewModel.setNavControllerNumber(3)
                                        }
                                        //关闭添加窗口
                                        coroutineScope.launch {
                                            sheetState.animateTo(ModalBottomSheetValue.Hidden)
                                            sheetState.hide()
                                        }

                                    },
                                    onDragCancel = {
                                        ifAddNewMission = false
                                        //动画
                                        coroutineScope.launch {
                                            dpOffState_x_l = localX
                                            dpOffState_y_l = 0.dp
                                            dpOffState_x_m = localX
                                            dpOffState_y_m = 0.dp
                                            boxState = !boxState
                                            offState = IntOffset(offState.x + 250, offState.y + 250)
                                            touch_x = 0f
                                            touch_y = 0f
                                            dpState_1 = 55.dp
                                            dpState_2 = 55.dp
                                            dpState_3 = 55.dp
                                        }

                                        //关闭添加窗口
                                        coroutineScope.launch {
                                            sheetState.animateTo(ModalBottomSheetValue.Hidden)
                                            sheetState.hide()
                                        }
                                    }
                                )
                            }) {
                        /* FAB content */
                        Image(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "Camera",
                            contentScale = ContentScale.Inside,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

            },
            floatingActionButtonPosition = FabPosition.Center,
            isFloatingActionButtonDocked = true,
            bottomBar = {
            }
        ) {

            ModalBottomSheetLayout(
                sheetState = sheetState,
                sheetShape = RoundedCornerShape(topEnd = 25.dp, topStart = 25.dp),
                sheetContent = {

                    AddTaskBottomSheetContent(
                        sheetState = sheetState,
                        viewModel = viewModel,
                        diaryViewModel = diaryViewModel,
                        item = item,
                        itemDiary = itemDiary,
                        navController = navController
                    )

                }) {
                // Screen content
                Nav(navController, viewModel, diaryViewModel, sheetState, onClick = {
                    item = it
                    coroutineScope.launch {
                        if (sheetState.isVisible) {
                            sheetState.animateTo(ModalBottomSheetValue.Hidden)
                        } else {
                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                        }
                    }

                }, onClickDiary = {
                    itemDiary = it
                })
            }
        }


    }
}

