package com.example.androidcourse.data

import androidx.compose.runtime.snapshots.SnapshotStateList

interface NoteRepository {
    fun newNote(title: String, text: String)
    fun getAllNotes(): SnapshotStateList<Note>
}