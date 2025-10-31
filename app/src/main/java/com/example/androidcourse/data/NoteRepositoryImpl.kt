package com.example.androidcourse.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

object NoteRepositoryImpl : NoteRepository {

    private val allNotes = mutableStateListOf<Note>()
    private var nextId = 0


    override fun newNote(title: String, text: String) {

        allNotes.add(Note(title, text, nextId++))
    }

    override fun getAllNotes(): SnapshotStateList<Note> {
        return allNotes
    }

    override fun getNoteById(id: Int): Note? {
        return allNotes.find { it.id == id }
    }


    override fun updateNote(id: Int, newTitle: String, newText: String): Boolean {
        val index = allNotes.indexOfFirst { it.id == id }
        if (index == -1) return false

        val oldNote = allNotes[index]
        val updatedNote = oldNote.copy(title = newTitle, text = newText)
        allNotes[index] = updatedNote
        return true
    }

    override fun deleteNote(id: Int): Boolean {
        return allNotes.removeIf { it.id == id }
    }
}