package com.myexample.presentation.note

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myexample.data.MyData.MyData
import com.myexample.utils.currentTime
import com.myexample.utils.vibrate
import com.myexample.utils.vibrate_2

/*
  **Created by 24606 at 13:59 2022.
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    item: MyData,
    complete: Boolean,
    homeScreen: Boolean,
    viewModel: MyViewModel
) {
    val context = LocalContext.current
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp)
            .clip(RoundedCornerShape(18.dp))
            .combinedClickable(
                onClick = {

                },
                onDoubleClick = {
                    vibrate_2(context)
                    val myData = item
                    myData.complete = complete
                    viewModel.update(myData)
                },
                onLongClick = {
                    vibrate(context)
                    val myData = item
                    myData.importance = !myData.importance
                    viewModel.update(myData)
                }
            ),
        elevation = 6.dp,
        backgroundColor =
        if (!item.complete) {
            if (item.importance) Color.Red else Color.Cyan
        } else {
            Color.Gray
        }

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.padding(10.dp, 8.dp)) {
                MyTitleText(text = item.title, complete)
                MyTitleText(text = item.detail, complete)
            }


            Row() {
                if (!homeScreen) {
                    IconButton(onClick = {
                        vibrate(context)
                        val myData = item
                        myData.date = currentTime.formatTime()
                        viewModel.update(myData)
                    }) {
                        Text(text = "2")
                    }
                }
                IconButton(onClick = {
                    vibrate(context)
                    item.id?.let { viewModel.deleteById(it) }
                }) {
                    Text(text = "1")
                }
                Checkbox(checked = item.complete, onCheckedChange = {
                    vibrate_2(context)
                    val myData = item
                    myData.complete = complete
                    viewModel.update(myData)
                })
            }

        }

    }
}

@Composable
fun MyTitleText(
    text: String,
    complete: Boolean
) {
    Text(
        text = text,
        textDecoration = if (!complete) TextDecoration.LineThrough else null,
        maxLines = 1
    )
}
