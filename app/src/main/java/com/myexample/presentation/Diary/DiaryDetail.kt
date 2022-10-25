package com.myexample.presentation.Diary

import android.annotation.SuppressLint
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.Note.SheetHandle
import com.myexample.presentation.ui.theme.Purple
import com.myexample.utils.constant
import com.myexample.utils.currentTime
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 20:24 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DiaryDetail(
    sheetState: ModalBottomSheetState,
    diaryViewModel: DirayViewModel
) {
//    val state by diaryViewModel.getById(id).collectAsState(MyDiary())
    diaryViewModel.getById(1)
    val state by diaryViewModel.state.collectAsState()
    var coroutineScope = rememberCoroutineScope()


//    Text(text = item.id.toString())

    val id = diaryViewModel.id
    val title = diaryViewModel.title
    val detail = diaryViewModel.detail
    val date = diaryViewModel.date
    val dateDetail = diaryViewModel.dateDetail
    val mood = diaryViewModel.mood

    val myDiary = MyDiary(
        id = id.value,
        title = title.value,
        detail = detail.value,
        date = date.value,
        dateDetail = dateDetail.value,
        mood = mood.value
    )

    LaunchedEffect(key1 = sheetState.isVisible) {
        if (!sheetState.isVisible && date.value != currentTime.formatTime()) {
            id.value = null
            title.value = ""
            detail.value = ""
            date.value = currentTime.formatTime()
            mood.value = Mood.AWESOME
        }
    }

    Column(
        modifier = Modifier
            .defaultMinSize(minHeight = 1.dp)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SheetHandle(Modifier.align(Alignment.CenterHorizontally))

        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = date.value,
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
            )
            Button(
                onClick = {
                    if (date.value == "") {
                        date.value = currentTime.formatTime()
                    }
                    myDiary.title = title.value
                    myDiary.detail = detail.value
                    myDiary.date = date.value
                    myDiary.dateDetail = currentTime.formatTimeDetail()
                    myDiary.mood = mood.value
                    if (title.value != "") {
                        diaryViewModel.insert(myDiary)
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
        Spacer(
            Modifier.height(16.dp)
        )

        EntryMoodSection(
            currentMood = mood.value,
        ) {
            mood.value = it
            myDiary.mood = mood.value
//            item.date = currentTime.formatTime()
            myDiary.dateDetail = currentTime.formatTimeDetail()
            diaryViewModel.update(myDiary)
        }

        //title
        OutlinedTextField(
            value = title.value,
            onValueChange = {
                title.value = it
                myDiary.title = it
//                item.date = currentTime.formatTime()
                myDiary.dateDetail = currentTime.formatTimeDetail()
                diaryViewModel.update(myDiary)
            },
            label = { Text("Title", style = MaterialTheme.typography.titleLarge) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1,
            textStyle = MaterialTheme.typography.titleLarge,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(25.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = date.value, onValueChange = {
            date.value = it
            myDiary.date = it
        })
        //content
        OutlinedTextField(
            value = detail.value,
            onValueChange = {
                detail.value = it
                myDiary.detail = it
//                item.date = currentTime.formatTime()
                myDiary.dateDetail = currentTime.formatTimeDetail()
                diaryViewModel.update(myDiary)
            },
            label = { Text("Content", style = MaterialTheme.typography.titleMedium) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(25.dp)
        )
        Spacer(modifier = Modifier.height(340.dp))

    }
}

@Composable
fun EntryMoodSection(
    currentMood: Mood,
    onMoodChange: (Mood) -> Unit
) {
    val moods = listOf(Mood.AWESOME, Mood.GOOD, Mood.OKAY, Mood.BAD, Mood.TERRIBLE)
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        moods.forEach { mood ->
            MoodItem(
                mood = mood,
                chosen = mood == currentMood,
                onMoodChange = { onMoodChange(mood) }
            )
        }
    }
}

@Composable
private fun MoodItem(mood: Mood, chosen: Boolean, onMoodChange: () -> Unit) {
    Box(Modifier.clip(RoundedCornerShape(8.dp))) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clickable { onMoodChange() }
                .padding(6.dp)
        ) {
            Icon(
                painter = painterResource(id = mood.icon),
                contentDescription = mood.title,
                tint = if (chosen) mood.color else Color.Gray,
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = mood.title,
                color = if (chosen) mood.color else Color.Gray,
                style = androidx.compose.material.MaterialTheme.typography.body2
            )
        }
    }
}
