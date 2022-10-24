package com.mhss.app.mybrain.presentation.tasks

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.provider.SyncStateContract
import android.renderscript.RenderScript
import android.service.quicksettings.TileService
import androidx.annotation.StringRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.high
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.InputMode.Companion.Keyboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.NavController

import com.myexample.MainActivity
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.diary.DiaryDetail
import com.myexample.presentation.diary.DirayViewModel
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.note.Status
import com.myexample.presentation.note.toPriority
import com.myexample.presentation.test.CursorSelectionBehaviour
import com.myexample.presentation.ui.theme.Green
import com.myexample.presentation.ui.theme.Orange
import com.myexample.presentation.ui.theme.Purple
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.constant
import com.myexample.utils.currentTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.Task
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTaskBottomSheetContent(
    sheetState: ModalBottomSheetState,
    viewModel: MyViewModel,
    diaryViewModel: DirayViewModel,
    item: MyData,
    itemDiary: MyDiary,
    navController: NavController
) {

    when (viewModel.navController_Number.value) {
        0 -> {
            AddTaskBottomSheetContentNote(sheetState, viewModel, item)
        }
        1 -> {
            AddTaskBottomSheetContentNote(sheetState, viewModel, item)
        }
        2 -> {
            AddTaskBottomSheetContentNote(sheetState, viewModel, item)
        }
        3 -> {
            DiaryDetail(
                item = itemDiary,
                navController = navController,
                sheetState = sheetState,
                diaryViewModel = diaryViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTaskBottomSheetContentNote(
    sheetState: ModalBottomSheetState,
    viewModel: MyViewModel,
    item: MyData
) {


    val id = viewModel.id
    val title = viewModel.title
    val detail = viewModel.detail
    val date = viewModel.date
    val importance = viewModel.importance
    val complete = viewModel.complete
    val status = viewModel.status

    var detail_2 = viewModel.detail_2

    val myData = MyData(
        id = id.value,
        title = title.value,
        detail = detail.value,
        date = date.value,
        importance = importance.value,
        complete = complete.value,
        status = status.value
    )

    val priorities = listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
    var priority by rememberSaveable { mutableStateOf(Priority.LOW) }
    var coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = sheetState.isVisible) {
        if (!sheetState.isVisible) {
            id.value = null
            title.value = ""
            detail.value = "●"
        }
        detail_2.value =
            TextFieldValue(text = detail.value, selection = TextRange(detail.value.length))
    }


    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 1.dp)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current


        SheetHandle(Modifier.align(Alignment.CenterHorizontally))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Add Task",
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Button(
                onClick = {
                    focusManager.clearFocus()
                    if (date.equals("")) {
                        date.value = currentTime.formatTime()
                    }
//                    if (detail.equals("●")) {
//                        detail.value = ""
//                    }
                    val myData = MyData(
                        id = id.value,
                        title = title.value,
                        detail = dealText(detail_2.value.text),
                        importance = importance.value,
                        complete = complete.value,
                        date = date.value,
                        status = getStatus(priority)
                    )
                    if (!title.equals("")) {
                        viewModel.insert(myData)
                    }
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }, modifier = Modifier
                    .height(30.dp)
                    .width(90.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Purple)
            ) {
                Text(
                    text = "Add"
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        //数据


//        OutlinedTextField(
//            value = title,
//            onValueChange = { title = it },
//            label = { Text(text = "title") },
//            shape = RoundedCornerShape(15.dp),
//            textStyle = TextStyle(fontSize = 18.sp),
//            modifier = Modifier
//                .fillMaxWidth()
//        )
//        OutlinedTextField(
//            value = detail,
//            onValueChange = { detail = it },
//            label = { Text(text = "detail") },
//            shape = RoundedCornerShape(15.dp),
//            textStyle = TextStyle(fontSize = 15.sp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(150.dp)
//        )

        //title
        OutlinedTextField(
            value = title.value,
            onValueChange = {
                title.value = it

                if (date.equals("")) {
                    date.value = currentTime.formatTime()
                }
//                if (detail.equals("●")) {
//                    detail.value = ""
//                }
                val myData = MyData(
                    id = id.value,
                    title = title.value,
                    detail = dealText(detail_2.value.text),
                    importance = importance.value,
                    complete = complete.value,
                    date = date.value,
                    status = getStatus(priority)
                )
                if (!title.equals("")) {
                    viewModel.update(myData)
                }
            },
            label = {
                androidx.compose.material3.Text(
                    "Title",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxSize(),
            shape = RoundedCornerShape(25.dp),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )

        var ableToTextNextLine by remember {
            mutableStateOf(true)
        }

        //content
        OutlinedTextField(
            value = detail_2.value,
            onValueChange = {
                detail_2.value = it
                ableToTextNextLine = true
                if (date.equals("")) {
                    date.value = currentTime.formatTime()
                }
//                if (detail.equals("●")) {
//                    detail.value = ""
//                }
                val myData = MyData(
                    id = id.value,
                    title = title.value,
                    detail = dealText(detail_2.value.text),
                    importance = importance.value,
                    complete = complete.value,
                    date = date.value,
                    status = getStatus(priority)
                )
                if (!title.equals("")) {
                    viewModel.update(myData)
                }
            },
            label = {
                androidx.compose.material3.Text(
                    "Content",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxSize()
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(25.dp),
//            keyboardActions = KeyboardActions.Default
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
////            keyboardActions = KeyboardActions
            keyboardActions = KeyboardActions(onDone = {
//                detail_2.text += "\n●"
                if (ableToTextNextLine) {
                    val text = detail_2.value.text + "\n●"
                    val selection = TextRange(text.length)
                    val textFieldValue =
                        TextFieldValue(text = text, selection = selection)
                    detail_2.value = textFieldValue
                    ableToTextNextLine = false
                }
            })
        )
        Spacer(Modifier.height(12.dp))
        PriorityTabRow(
            priorities = priorities,
            selectedPriority = priority,
            onChange = {
                priority = it
                myData.status = getStatus(priority)
                myData.title = title.value
                myData.detail = dealText(detail_2.value.text)
                if (myData.title != "") {
                    viewModel.update(myData)
                }
            }
        )

        Spacer(Modifier.height(320.dp))


    }
}

fun dealText(text: String): String {
    var temp: String = ""
    if (text == "●") {
        temp = text
    } else if (text != "") {
        val list = text.split("\n")
        list.forEachIndexed { index, it ->
            if (it != "" && it[0] == '●' && it.length > 1) {
                if (index == list.size - 1) {
                    temp += it
                } else {
                    temp += it + "\n"
                }

            }
        }
    }

    return temp
}

enum class Priority(val title: String, val color: Color) {
    LOW("low", Green),
    MEDIUM("Medium", Orange),
    HIGH("High", Red)
}

fun Int.toPriority(): Priority {
    return when (this) {
        0 -> Priority.LOW
        1 -> Priority.MEDIUM
        2 -> Priority.HIGH
        else -> Priority.LOW
    }
}

fun Priority.toInt(): Int {
    return when (this) {
        Priority.LOW -> 0
        Priority.MEDIUM -> 1
        Priority.HIGH -> 2
    }
}

fun getStatus(priority: Priority): Status {
    when (priority) {
        Priority.LOW -> {
            return Status.INCOMPLETED_GREEN
        }
        Priority.MEDIUM -> {
            return Status.INCOMPLETED_ORANGE
        }
        Priority.HIGH -> {
            return Status.INCOMPLETED_RED
        }
        else -> {
            return Status.INCOMPLETED_GREEN
        }
    }
}

@Composable
fun PriorityTabRow(
    priorities: List<Priority>,
    selectedPriority: Priority,
    onChange: (Priority) -> Unit
) {
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        AnimatedTabIndicator(Modifier.tabIndicatorOffset(tabPositions[selectedPriority.toInt()]))
    }
    TabRow(
        selectedTabIndex = selectedPriority.toInt(),
        indicator = indicator,
        modifier = Modifier.clip(RoundedCornerShape(14.dp))
    ) {
        priorities.forEachIndexed { index, it ->
            Tab(
                text = { Text(it.title) },
                selected = selectedPriority.toInt() == index,
                onClick = {
                    onChange(index.toPriority())
                },
                modifier = Modifier.background(it.color)
            )
        }
    }
}


@Composable
fun AnimatedTabIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .padding(5.dp)
            .fillMaxSize()
            .border(BorderStroke(2.dp, Color.White), RoundedCornerShape(8.dp))
    )
}

@Composable
fun SheetHandle(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .size(width = 60.dp, height = 4.dp)
            .background(Color.Gray)
            .padding(5.dp)
    )
}

