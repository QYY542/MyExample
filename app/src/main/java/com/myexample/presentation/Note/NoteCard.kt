package com.myexample.presentation.Note

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myexample.R
import com.myexample.data.MyData.MyData
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.vibrate
import com.myexample.utils.vibrate_2
import kotlinx.coroutines.delay

/*
  **Created by 24606 at 13:59 2022.
*/

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier,
    item: MyData,
    viewModel: NoteViewModel,
    onClick: (item: MyData) -> Unit
) {
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()

    val myData = MyData(
        item.id,
        item.title,
        item.detail,
        item.importance,
        item.complete,
        item.date,
        item.status
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .combinedClickable(
                    onClick = {
                        //逻辑在外面写，执行在这里
                        onClick(item)
                    },
                    onDoubleClick = {
                        vibrate_2(context)

                        myData.complete = !myData.complete
                        if (myData.complete) {
                            myData.status = Status.COMPLETED
                        } else {
                            myData.status = Status.INCOMPLETED_GREEN
                        }

                        viewModel.insert(myData)
                    },
                    onLongClick = {
                        vibrate(context)
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
                                Status.COMPLETED
                            }
                        }
                        viewModel.insert(myData)


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
                    androidx.compose.material3.IconButton(onClick = {
                        vibrate_2(context)
                        myData.complete = !myData.complete
                        if (myData.complete) {
                            myData.status = Status.COMPLETED
                        } else {
                            myData.status = Status.INCOMPLETED_GREEN
                        }
                        viewModel.insert(myData)

                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(myData.status.icon),
                            myData.status.title,
                            tint = myData.status.color,
                            modifier = Modifier.size(30.dp)
                        )

                    }

                    Spacer(Modifier.width(8.dp))

                    MyTitleText(text = myData.title, complete = myData.complete)


                }
                var doubleClick by remember {
                    mutableStateOf(0)
                }
                LaunchedEffect(key1 = doubleClick) {
                    delay(300)
                    doubleClick = 0
                }
                Row(verticalAlignment = Alignment.CenterVertically) {

                    androidx.compose.material3.IconButton(onClick = {
                        doubleClick++
                        vibrate_2(context)
                        if (doubleClick % 2 == 0) {
                            item.id?.let { viewModel.deleteById(it) }
                        }

                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(R.drawable.ic_delete),
                            myData.status.title,
                            tint = Red,
                            modifier = Modifier.size(30.dp)
                        )

                    }
                }
            }

            if (item.detail.isNotBlank() && item.detail != "●") {
                Spacer(Modifier.height(8.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(8.dp))
                    MyDetialText(text = myData.detail, complete = myData.complete)
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

