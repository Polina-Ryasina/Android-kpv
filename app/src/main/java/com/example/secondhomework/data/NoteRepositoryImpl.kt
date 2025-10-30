package com.example.secondhomework.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object NoteRepositoryImpl : NoteRepository {

    private val allNotes = mutableStateListOf<Note>()

    override fun newNote(title: String, text: String) {
        allNotes.add(Note(title, text))
    }

    override fun getAllNotes(): SnapshotStateList<Note> {
        return allNotes
    }
}