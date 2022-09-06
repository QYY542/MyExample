package com.myexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.myexample.data.MyData
import com.myexample.ui.theme.MyExampleTheme
import com.myexample.viewModel.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyExampleTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel: MyViewModel by viewModels()
                    val state by viewModel.state.collectAsState()

                    Column(Modifier.fillMaxSize()) {

                        ButtonList(viewModel)

                        Text(text = "Success")
                        LazyColumn(state = rememberLazyListState()) {
                            items(items = state) { item ->
                                Row() {
                                    Column() {
                                        Text(text = item.id.toString())
                                        Text(text = item.date)
                                        Text(text = item.message)
                                    }
                                    Button(onClick = {
                                        item.id?.let { viewModel.deleteById(it) }
                                    }) {
                                        Text(text = "delete")
                                    }
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}

@Composable
fun ButtonList(
    viewModel: MyViewModel
) {
    var id = remember {
        mutableStateOf(1)
    }
    Button(onClick = {
        val myData = MyData(id.value)
        viewModel.insert(myData)
        id.value++
    }) {
        Text(text = "insert+${id.value}")
    }

    Button(onClick = {
        viewModel.deleteAll()
    }) {
        Text(text = "delete_all")
    }

    Button(onClick = {
        val myData = MyData(3, "123", "553")
        viewModel.update(myData)
    }) {
        Text(text = "update")
    }
}

//@Composable
//fun HomeScreen(
//    viewModel: MyViewModel
//) {
//    val state by viewModel.state.collectAsState()
//
//    when (state) {
//        is RequestState.Success -> {
//            Column() {
//                Text(text = "Success")
//                LazyColumn() {
//                    items((state as RequestState.Success<List<Detail>>).data) { item ->
//                        Column() {
//                            Text(text = item.id.toString())
//                            Text(text = item.date)
//                            Text(text = item.message)
//                        }
//                    }
//                }
//            }
//        }
//        is RequestState.Empty -> {
//            Text(text = "Empty")
//        }
//        is RequestState.Failure -> {
//            Text(text = "Failure")
//        }
//        is RequestState.Loading -> {
//            Text(text = "Loading")
//        }
//    }
//
//}