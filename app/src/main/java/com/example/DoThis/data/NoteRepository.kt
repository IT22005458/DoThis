package com.example.DoThis.data

import androidx.lifecycle.LiveData
import com.example.DoThis.database.NoteDAO

class  NoteRepository(private val noteDAO: NoteDAO) {

    val allNotes: LiveData<List<Note>> = noteDAO.getAllNotes()
    //to insert a note
    suspend fun insert(note: Note) = noteDAO.insertNote(note)

    //to delete a note
    suspend fun delete(note: Note) = noteDAO.deleteNote(note)

    //to update a note
    suspend fun update(note: Note) = noteDAO.updateNote(note)
}