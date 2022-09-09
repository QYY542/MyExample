package com.myexample.presentation.ttarget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
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
import com.myexample.data.MyTarget.MyTarget
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.target.TargetViewModel

/*
  **Created by 24606 at 23:39 2022.
*/

@Composable
fun TargetScreen(
    navController: NavController,
    viewModel: MyViewModel,
    targetViewModel: TargetViewModel = hiltViewModel()
) {
    viewModel.setNavControllerNumber(2)
    navController.enableOnBackPressed(false)
    Text(text = "Target")

    val state by targetViewModel.state.collectAsState()


    Column(Modifier.fillMaxSize()) {
        Button(onClick = {
            val myTarget = MyTarget()
            targetViewModel.insert(myTarget)
        }) {
            Text(text = "Add Target")
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

