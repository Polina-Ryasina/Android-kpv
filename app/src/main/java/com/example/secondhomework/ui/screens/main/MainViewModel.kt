package com.example.secondhomework.ui.screens.main

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.secondhomework.data.Note
import com.example.secondhomework.data.NoteRepositoryImpl

class MainViewModel: ViewModel() {
    val allNotes: SnapshotStateList<Note> = NoteRepositoryImpl.getAllNotes()

    fun newNote(title: String, text: String) {
        NoteRepositoryImpl.newNote(title, text)
    }

}