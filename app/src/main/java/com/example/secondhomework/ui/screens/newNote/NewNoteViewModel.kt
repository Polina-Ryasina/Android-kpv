package com.example.secondhomework.ui.screens.newNote

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.secondhomework.R

class NewNoteViewModel: ViewModel() {
    var title by mutableStateOf("")
        private set

    var text by mutableStateOf("")
        private set

    var blankTitle by mutableStateOf(0)
        private set

    fun onTitleChange(newTitle: String) {
        title = newTitle
        if (blankTitle != 0) {
            blankTitle = 0
        }
    }

    fun onTextChange(newText: String) {
        text = newText
    }

    fun titleFieldCorrect(): Boolean {
        var isCorrect = true
        if (title.isBlank()) {
            blankTitle = R.string.blankTitle
            isCorrect = false
        }
        return isCorrect
    }
}