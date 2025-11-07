package com.example.androidcourse.ui.screens.thirdscreen

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.androidcourse.R
import com.example.androidcourse.helpers.MessageStorage

class ThirdScreenViewModel : ViewModel() {

    var messages = MessageStorage.messages
        private set

    var currentText by mutableStateOf("")
        private set

    var textError by mutableStateOf(0)
        private set

    fun onTextChange(newText: String) {
        currentText = newText
        if (currentText.isNotBlank()) {textError = 0}
    }

    fun addMessage(message: String) {
        if (message.isNotBlank()) {
            MessageStorage.messages.add(message)
            currentText = ""
        } else {
            textError = R.string.message_error
        }
    }
}
