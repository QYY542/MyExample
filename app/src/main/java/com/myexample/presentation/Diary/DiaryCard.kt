package com.myexample.presentation.Diary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.myexample.R
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.vibrate_2
import kotlinx.coroutines.delay

/*
  **Created by 24606 at 16:09 2022.
*/

@Composable
fun DiaryCard(
    modifier: Modifier,
    item: MyDiary,
    diaryViewModel: DiaryViewModel,
    onClick: (item: MyDiary) -> Unit
) {
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()

    val myDiary = MyDiary(
        item.id,
        item.title,
        item.detail,
        item.date,
        item.dateDetail,
        item.mood
    )

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(25.dp),
        elevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    onClick(myDiary)
                }
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(myDiary.mood.icon),
                        myDiary.mood.title,
                        tint = myDiary.mood.color,
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        myDiary.title,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                        fontSize = 18.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
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
                        vibrate_2(context)
                        if (doubleClick % 2 == 0) {
                            myDiary.id?.let { diaryViewModel.deleteById(it) }
                        }
                    }, modifier = Modifier.size(30.dp)) {

                        Icon(
                            painterResource(R.drawable.ic_delete),
                            "delete",
                            tint = Red,
                            modifier = Modifier.size(30.dp)
                        )

                    }

                }

            }
            if (myDiary.detail.isNotBlank()) {
                Spacer(Modifier.height(3.dp))
                Row(Modifier.fillMaxWidth()) {
                    Spacer(Modifier.width(8.dp))
                    Text(
                        myDiary.detail,
                        style = MaterialTheme.typography.body2,
                        fontSize = 15.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(8.dp))
                }
            }
            Spacer(Modifier.height(3.dp))
            Text(
                text = myDiary.date,
                style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.End),
                fontFamily = FontFamily(Font(R.font.rubik_bold)),
            )
        }
    }
}
