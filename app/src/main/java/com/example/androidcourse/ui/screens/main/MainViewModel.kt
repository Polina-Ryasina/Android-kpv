package com.example.androidcourse.ui.screens.main

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.androidcourse.data.Note
import com.example.androidcourse.data.NoteRepositoryImpl

class MainViewModel: ViewModel() {
    val allNotes: SnapshotStateList<Note> = NoteRepositoryImpl.getAllNotes()

}