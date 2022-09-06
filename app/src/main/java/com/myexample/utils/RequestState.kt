package com.myexample.utils

/*
  **Created by 24606 at 13:53 2022.
*/

sealed class RequestState<out T> {
    data class Success<T>(var data: T) : RequestState<T>()
    data class Failure(val error: Throwable) : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    object Empty : RequestState<Nothing>()
}