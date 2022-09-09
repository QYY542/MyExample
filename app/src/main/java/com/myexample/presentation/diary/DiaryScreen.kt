package com.myexample.presentation.diary

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.BottomAppBarDefaults.FloatingActionButtonElevation.elevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.myexample.R
import com.myexample.data.MyDiary.MyDiary
import com.myexample.presentation.note.MyViewModel
import com.myexample.presentation.note.NoteHome
import com.myexample.presentation.ui.theme.Red
import com.myexample.utils.constant
import com.myexample.utils.vibrate
import com.myexample.utils.vibrate_2
import kotlinx.coroutines.launch

/*
  **Created by 24606 at 23:37 2022.
*/

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DiaryScreen(
    navController: NavController,
    sheetState: ModalBottomSheetState,
    viewModel: MyViewModel,
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    viewModel.setNavControllerNumber(3)
    navController.enableOnBackPressed(false)
    DirayHome(sheetState)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class
)
@Composable
fun DirayHome(
    sheetState: ModalBottomSheetState,
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    val state by dirayViewModel.state.collectAsState()
    val context = LocalContext.current
    var coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(
                            text = "Diary",
                            fontFamily = FontFamily(Font(R.font.rubik_bold)),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                },
                backgroundColor = Color.White

            )
        }
    ) {


        Column(Modifier.fillMaxSize()) {
//            Button(onClick = {
//                val myDiary = MyDiary()
//                dirayViewModel.insert(myDiary)
//            }) {
//                Text(text = "Add Diary")
//            }

            Spacer(modifier = Modifier.height(60.dp))
            val listState = rememberLazyListState()

            Column(modifier = Modifier.fillMaxSize()) {
                LazyColumn(
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    itemsIndexed(state) { index, item ->


                        ///
                        Card(
                            modifier = Modifier
                                .animateItemPlacement(),
                            shape = RoundedCornerShape(25.dp),
                            elevation = 8.dp
                        ) {
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        coroutineScope.launch {
                                            constant.onAddButton = false
                                            dirayViewModel.id.value = item.id
                                            constant.selectId = item.id
                                            sheetState.animateTo(ModalBottomSheetValue.Expanded)
                                        }
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
                                            painterResource(item.mood.icon),
                                            item.mood.title,
                                            tint = item.mood.color,
                                            modifier = Modifier.size(30.dp)
                                        )
                                        Spacer(Modifier.width(8.dp))
                                        Text(
                                            item.title,
                                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                                            fontSize = 18.sp,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    Row(verticalAlignment = Alignment.CenterVertically) {

                                        androidx.compose.material3.IconButton(onClick = {

                                            vibrate_2(context)
                                            item.id?.let { dirayViewModel.deleteById(it) }


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
                                if (item.detail.isNotBlank()) {
                                    Spacer(Modifier.height(3.dp))
                                    Text(
                                        item.detail,
                                        style = MaterialTheme.typography.body2,
                                        fontSize = 15.sp,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                Spacer(Modifier.height(3.dp))
                                Text(
                                    text = item.dateDetail,
                                    style = MaterialTheme.typography.body2.copy(fontWeight = FontWeight.Bold),
                                    modifier = Modifier.align(Alignment.End),
                                    fontFamily = FontFamily(Font(R.font.rubik_bold)),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DiaryInfo(
    dirayViewModel: DirayViewModel = hiltViewModel()
) {
    BoxText("DiaryInfo")
}

@Composable
fun BoxText(text: String) {
    Box(Modifier.fillMaxSize()) {
        Text(text = text, modifier = Modifier.align(Alignment.Center))
    }
}

