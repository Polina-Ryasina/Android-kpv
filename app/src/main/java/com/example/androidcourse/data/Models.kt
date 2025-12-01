package com.example.androidcourse.data

enum class Disp { DEFAULT, IO, MAIN, UNCONFINED }


data class CoroutineOptions(
    val count: Int = 10,
    val dispatcher: Disp = Disp.DEFAULT,
    val sequential: Boolean = true,
    val parallel: Boolean = false,
    val delayedStart: Boolean = false
)


class ToastException(message: String) : Exception(message)
class SnackbarException(message: String) : Exception(message)
class ResetException(message: String) : Exception(message)