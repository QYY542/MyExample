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
import androidx.compose.ui.unit.sp
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.presentation.ui.theme.Gray
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.constant
import com.myexample.utils.constant.onAddButtonChange
import com.myexample.utils.currentTime
import com.myexample.utils.vibrate
import com.myexample.utils.vibrate_2
import kotlinx.coroutines.delay

/*
  **Created by 24606 at 13:59 2022.
*/

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier,
    itemi: MyData,
    complete: Boolean,
    homeScreen: Boolean,
    viewModel: MyViewModel,
    onClick: (item: MyData) -> Unit
) {
    val context = LocalContext.current
    var item by remember {
        mutableStateOf(itemi)
    }

    var status by remember {
        mutableStateOf(item.status)
    }
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        onClick(MyData())
                        onClick(item)
                        constant.onAddButton = false
                        onAddButtonChange++
                    },
                    onDoubleClick = {
                        vibrate_2(context)
                        item.complete = complete
                        if (item.complete) {
                            item.status = Status.COMPLETED
                        } else {
                            item.status = Status.INCOMPLETED_GREEN
                        }

                        viewModel.insert(item)
                    },
                    onLongClick = {
                        vibrate(context)
//                    myData.importance = !myData.importance
                        if (complete) {
                            item.status = when (item.status) {
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
                            viewModel.insert(item)
                        }

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
                        item.complete = complete
                        if (item.complete) {
                            item.status = Status.COMPLETED
                        } else {
                            item.status = Status.INCOMPLETED_GREEN
                        }

                        status = when (status) {
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
                        item.status = status
                        viewModel.insert(item)

                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(status.icon),
                            status.title,
                            tint = status.color,
                            modifier = Modifier.size(30.dp)
                        )

                    }

                    Spacer(Modifier.width(8.dp))

                    MyTitleText(text = item.title, complete = complete)


                }
                var doubleClick by remember {
                    mutableStateOf(0)
                }
                LaunchedEffect(key1 = doubleClick) {
                    delay(300)
                    doubleClick = 0
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(onClick = {
                        doubleClick++
                        vibrate(context)
                        if (doubleClick % 2 == 0) {
                            vibrate_2(context)
                            item.id?.let { viewModel.deleteById(it) }
                        }

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
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(8.dp))
                    MyDetialText(text = item.detail, complete = complete)
                    Spacer(Modifier.height(8.dp))
                }


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
        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
        fontSize = 18.sp,
        maxLines = 1,
        textDecoration = if (complete) TextDecoration.LineThrough else null,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth(0.8f)
    )
}

@Composable
fun MyDetialText(
    text: String,
    complete: Boolean
) {
    Text(
        text = text,
        style = MaterialTheme.typography.body2,
        fontSize = 15.sp,
        maxLines = 3,
        textDecoration = if (complete) TextDecoration.LineThrough else null,
        overflow = TextOverflow.Ellipsis
    )
}

