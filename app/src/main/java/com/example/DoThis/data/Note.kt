package com.example.DoThis.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//making a table(entity) for database storage
@Entity(tableName = "notes_table")
//defining the schema of the table text as string
data class Note(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val title: String?,
    val des: String?,
    var color: Int?,
    var dueDate: Long? // Add due date property
): Serializable

