package com.example.DoThis.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.DoThis.data.Note

@Dao
interface NoteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Query("SELECT * FROM notes_table")
    fun getAllNotes(): LiveData<List<Note>>
}