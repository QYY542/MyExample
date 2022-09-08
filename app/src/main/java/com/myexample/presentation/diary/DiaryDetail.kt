package com.myexample.presentation.diary

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.createBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.myexample.data.MyDiary.MyDiary
import com.myexample.utils.currentTime

/*
  **Created by 24606 at 20:24 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryDetail(
    id: Int,
    navController: NavController,
    diaryViewModel: DirayViewModel = hiltViewModel()
) {
    navController.enableOnBackPressed(true)
//    val state by diaryViewModel.getById(id).collectAsState(MyDiary())
    diaryViewModel.getById(1)
    val state by diaryViewModel.state.collectAsState()

    var item by remember {
        mutableStateOf(MyDiary())
    }
    

    state.forEach{
        if(it.id == id){
            item  = it
        }
    }

//    Text(text = item.id.toString())

    var id by remember {
        mutableStateOf(item.id)
    }
    var title by remember {
        mutableStateOf(item.title)
    }
    var detail by remember {
        mutableStateOf(item.detail)
    }
    var date by remember {
        mutableStateOf(item.date)
    }


    id = item.id
    title = item.title
    detail = item.detail
    date = item.date

    Scaffold(topBar = {
        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Text(text = "新建日记")
            Button(onClick = { navController.navigate("diary_screen") }) {
                Text(text = "返回")
            }
        }

    }) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp)
    ) {
        Spacer(modifier = Modifier.height(50.dp))






        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                item.title = it
                item.date = currentTime.formatTime()
                item.dateDetail = currentTime.formatTimeDetail()
                diaryViewModel.update(item)
                            },
            label = { Text("标题", style = MaterialTheme.typography.titleLarge) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1,
            textStyle = MaterialTheme.typography.titleLarge,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            value = detail,
            onValueChange = {
                detail = it
                item.detail = it
                item.date = currentTime.formatTime()
                item.dateDetail = currentTime.formatTimeDetail()
                diaryViewModel.update(item)
                            },
            label = { Text("内容", style = MaterialTheme.typography.titleMedium) },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.primary
            ),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxSize(),
            shape = RoundedCornerShape(20.dp)
        )
    }
}
}
@Preview
@Composable
fun DiaryDetailPreview() {
}