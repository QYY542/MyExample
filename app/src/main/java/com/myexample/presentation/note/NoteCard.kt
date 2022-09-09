package com.myexample.presentation.note

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material3.BottomAppBarDefaults.FloatingActionButtonElevation.elevation
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.presentation.ui.theme.Gray
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.currentTime
import com.myexample.utils.vibrate
import com.myexample.utils.vibrate_2

/*
  **Created by 24606 at 13:59 2022.
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier,
    item: MyData,
    complete: Boolean,
    homeScreen: Boolean,
    viewModel: MyViewModel,
    onClick: (item: MyData) -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        onClick(item)
                    },
                    onDoubleClick = {
                        vibrate_2(context)
                        val myData = item
                        myData.complete = complete
                        if (myData.complete) {
                            myData.status = Status.COMPLETED
                        } else {
                            myData.status = Status.INCOMPLETED_GREEN
                        }

                        viewModel.update(myData)
                    },
                    onLongClick = {
                        vibrate(context)
                        val myData = item
//                    myData.importance = !myData.importance
                        myData.status = when (myData.status) {
                            Status.INCOMPLETED_GREEN -> {
                                Status.INCOMPLETED_ORANGE
                            }
                            Status.INCOMPLETED_ORANGE -> {
                                Status.INCOMPLETED_RED
                            }
                            Status.INCOMPLETED_RED -> {
                                Status.INCOMPLETED_GREEN
                            }
                            else -> {
                                Status.INCOMPLETED_GREEN
                            }
                        }
                        viewModel.update(myData)
                    }
                )
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        vibrate_2(context)
                        val myData = item
                        myData.complete = complete
                        if (myData.complete) {
                            myData.status = Status.COMPLETED
                        } else {
                            myData.status = Status.INCOMPLETED_GREEN
                        }
                        viewModel.update(myData)

                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(item.status.icon),
                            item.status.title,
                            tint = item.status.color,
                            modifier = Modifier.size(30.dp)
                        )

                    }

                    Spacer(Modifier.width(8.dp))

                    Text(
                        item.title,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                }

                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = {

                        vibrate_2(context)
                        item.id?.let { viewModel.deleteById(it) }


                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(R.drawable.ic_delete),
                            item.status.title,
                            tint = Red,
                            modifier = Modifier.size(30.dp)
                        )

                    }

                }
            }

            if (item.detail.isNotBlank()) {
                Spacer(Modifier.height(8.dp))
                Text(
                    item.detail,
                    style = MaterialTheme.typography.body2,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = item.date,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
//    Card(
//        Modifier
//            .fillMaxWidth()
//            .padding(10.dp, 5.dp)
//            .clip(RoundedCornerShape(25.dp))
//            .combinedClickable(
//                onClick = {
//
//                },
//                onDoubleClick = {
//                    vibrate_2(context)
//                    val myData = item
//                    myData.complete = complete
//                    viewModel.update(myData)
//                },
//                onLongClick = {
//                    vibrate(context)
//                    val myData = item
//                    myData.importance = !myData.importance
//                    viewModel.update(myData)
//                }
//            ),
//        elevation = 8.dp,
//        backgroundColor =
//        if (!item.complete) {
//            if (item.importance) Color.Red else Color.Cyan
//        } else {
//            Color.Gray
//        }
//
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(Modifier.padding(10.dp, 8.dp)) {
//                MyTitleText(text = item.title, complete)
//                MyTitleText(text = item.detail, complete)
//            }
//
//
//            Row() {
//                if (!homeScreen) {
//                    IconButton(onClick = {
//                        vibrate(context)
//                        val myData = item
//                        myData.date = currentTime.formatTime()
//                        viewModel.update(myData)
//                    }) {
//                        Text(text = "2")
//                    }
//                }
//                IconButton(onClick = {
//                    vibrate(context)
//                    item.id?.let { viewModel.deleteById(it) }
//                }) {
//                    Text(text = "1")
//                }
//                Checkbox(checked = item.complete, onCheckedChange = {
//                    vibrate_2(context)
//                    val myData = item
//                    myData.complete = complete
//                    viewModel.update(myData)
//                })
//            }
//
//        }
//
//    }
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

