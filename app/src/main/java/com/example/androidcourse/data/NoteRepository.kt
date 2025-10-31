package com.example.androidcourse.data

import androidx.compose.runtime.snapshots.SnapshotStateList

interface NoteRepository {
    fun newNote(title: String, text: String)
    fun getAllNotes(): SnapshotStateList<Note>
    fun getNoteById(id: Int): Note?
    fun updateNote(id: Int, newTitle: String, newText: String): Boolean
    fun deleteNote(id: Int): Boolean
}