package com.myexample.presentation.test.inputting_taskbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview

/*
  **Created by 24606 at 18:58 2022.
*/

@Composable
fun inputting() {
    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Bottom) {
        var text by remember() {
            mutableStateOf("hello")
        }
        TextField(value = text, onValueChange = {
            text = it
        })
        TextField(value = text, onValueChange = {
            text = it
        })
    }

}



