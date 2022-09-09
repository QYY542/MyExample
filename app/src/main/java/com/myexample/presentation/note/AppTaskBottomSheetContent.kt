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
import androidx.compose.material.*
import androidx.compose.material.ContentAlpha.high
import androidx.compose.material.ContentAlpha.medium
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

import com.myexample.MainActivity
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.note.Status
import com.myexample.presentation.ui.theme.Green
import com.myexample.presentation.ui.theme.Orange
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.currentTime
import kotlinx.coroutines.launch
import okhttp3.internal.concurrent.Task
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddTaskBottomSheetContent(
    sheetState: ModalBottomSheetState,
    viewModel: MyViewModel,
    item: MyData
) {
//    var id by viewModel.id
//    var title by viewModel.title
//    var detail by viewModel.detail
//    var importance by viewModel.importance
//    var complete by viewModel.complete
//    var date by viewModel.date
//    val priorities = listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
//    var priority by rememberSaveable { mutableStateOf(Priority.LOW) }
//    var coroutineScope = rememberCoroutineScope()

    var id: Int? by remember {
        mutableStateOf(1)
    }
    var title by remember {
        mutableStateOf("")
    }
    var detail by remember {
        mutableStateOf("")
    }
    var importance by remember {
        mutableStateOf(false)
    }
    var complete by remember {
        mutableStateOf(false)
    }
    var date by remember {
        mutableStateOf("2022-9-9")
    }

    LaunchedEffect(key1 = item) {
        id = item.id
        title = item.title
        detail = item.detail
        title = item.title
        importance = item.importance
        complete = item.complete
    }

    val priorities = listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
    var priority by rememberSaveable { mutableStateOf(Priority.LOW) }
    var coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 1.dp)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SheetHandle(Modifier.align(Alignment.CenterHorizontally))
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Add Task",
                style = MaterialTheme.typography.h5
            )
            Button(
                onClick = {
                    if (date.equals("")) {
                        date = currentTime.formatTime()
                    }
                    val myData = MyData(
                        id = id,
                        title = title,
                        detail = detail,
                        importance = importance,
                        complete = complete,
                        date = date,
                        status = getStatus(priority)
                    )
                    if (!title.equals("")) {
                        viewModel.insert(myData)
                    }
                    title = ""
                    detail = ""
                    coroutineScope.launch {
                        sheetState.hide()
                    }
                }, modifier = Modifier
                    .height(30.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Add"
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        //数据


        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text(text = "title") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
        )
        OutlinedTextField(
            value = detail,
            onValueChange = { detail = it },
            label = { Text(text = "detail") },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )
        Spacer(Modifier.height(12.dp))
        PriorityTabRow(
            priorities = priorities,
            selectedPriority = priority,
            onChange = { priority = it }
        )

        Spacer(Modifier.height(320.dp))


    }
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

