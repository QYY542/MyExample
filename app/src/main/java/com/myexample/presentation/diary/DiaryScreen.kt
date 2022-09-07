package com.myexample.presentation.diary

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.myexample.data.MyData.MyData
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.note.MyViewModel

/*
  **Created by 24606 at 23:37 2022.
*/

@Composable
fun DiaryScreen(
    navController: NavController,
    viewModel: MyViewModel,
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    viewModel.setNavControllerNumber(3)
    Text(text = "Diary")
    val state by dirayViewModel.state.collectAsState()


    Column(Modifier.fillMaxSize()) {
        Button(onClick = {
            val myDiary = MyDiary()
            dirayViewModel.insert(myDiary)
        }) {
            Text(text = "Add Diary")
        }
        LazyColumn() {
            itemsIndexed(state) { index, item ->
                Card(
                    Modifier
                        .fillMaxWidth()
                        .padding(10.dp, 5.dp)
                        .clip(RoundedCornerShape(18.dp)),
                    elevation = 6.dp,
                    backgroundColor = Color.Gray
                ) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(Modifier.padding(10.dp, 8.dp)) {
                            Text(text = "${item.id}")
                            Text(text = item.title)
                            Text(text = item.detail)
                        }
                    }

                }
            }
        }
    }

}

