package com.myexample.presentation.Tasks

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myexample.R
import com.myexample.presentation.ui.theme.Green
import com.myexample.presentation.ui.theme.Orange
import com.myexample.presentation.ui.theme.Purple
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.currentTime
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 8:55 2022.
*/

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskDetail(
    sheetState: ModalBottomSheetState,
    taskViewModel: TaskViewModel,
) {


    var id by taskViewModel.id
    var title by taskViewModel.title
    var detail by taskViewModel.detail
    var date by taskViewModel.date
    var importance by taskViewModel.importance
    var complete by taskViewModel.complete
    var status by taskViewModel.status
    var priority by taskViewModel.priority
    var myData by taskViewModel.myData

    var detail_2 by taskViewModel.detail_2


    val priorities = listOf(Priority.LOW, Priority.MEDIUM, Priority.HIGH)
    var coroutineScope = rememberCoroutineScope()

//


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
            androidx.compose.material.Text(
                text = "Add Task",
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Button(
                onClick = {
                    focusManager.clearFocus()
                    if (date == "") {
                        date = currentTime.formatTime()
                    }
//                    if (detail.equals("●")) {
//                        detail.value = ""
//                    }
                    myData.title = title
                    myData.detail = dealText(detail_2.text)
                    myData.importance = importance
                    myData.complete = complete
                    myData.date = date
                    if (myData.complete) {
                        myData.status = Status.COMPLETED
                    } else {
                        myData.status = getStatus(priority)
                    }
                    if (title != "") {
                        taskViewModel.insert(myData)
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
                androidx.compose.material.Text(
                    text = "Add"
                )
            }
        }
        Spacer(Modifier.height(16.dp))

        //title
        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it

                if (date == "") {
                    date = currentTime.formatTime()
                }
                myData.title = title
                myData.detail = dealText(detail_2.text)
                if (myData.complete) {
                    myData.status = Status.COMPLETED
                } else {
                    myData.status = getStatus(priority)
                }
                if (title != "") {
                    taskViewModel.update(myData)
                }
            },
            label = {
                Text(
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

        Spacer(modifier = Modifier.height(8.dp))
        //content
        OutlinedTextField(
            value = detail_2,
            onValueChange = {
                detail_2 = it
                ableToTextNextLine = true
                if (date == "") {
                    date = currentTime.formatTime()
                }
                if (detail_2.text == "") {
                    detail = "●"
                    detail_2 =
                        TextFieldValue(
                            text = detail,
                            selection = TextRange(detail.length)
                        )
                }
                myData.title = title
                myData.detail = dealText(detail_2.text)
                if (myData.complete) {
                    myData.status = Status.COMPLETED
                } else {
                    myData.status = getStatus(priority)
                }
                if (title != "") {
                    taskViewModel.update(myData)
                }
            },
            label = {
                Text(
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
                    val text = detail_2.text + "\n●"
                    val selection = TextRange(text.length)
                    val textFieldValue =
                        TextFieldValue(text = text, selection = selection)
                    detail_2 = textFieldValue
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
                myData.title = title
                myData.detail = dealText(detail_2.text)
                if (myData.complete) {
                    myData.status = Status.COMPLETED
                } else {
                    myData.status = getStatus(priority)
                }
                if (myData.title != "") {
                    taskViewModel.update(myData)
                }
            }
        )
        Spacer(Modifier.height(320.dp))
    }
}

fun dealText(text: String): String {
    var temp: String = ""
    var count = 0
    if (text == "●") {
        temp = text
    } else if (text != "") {
        val list = text.split("\n").filter { it != "●" && it != "" }
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
                text = { androidx.compose.material.Text(it.title) },
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
