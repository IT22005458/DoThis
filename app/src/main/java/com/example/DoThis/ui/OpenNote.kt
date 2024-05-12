package com.example.DoThis.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.DoThis.data.Note
import com.example.DoThis.databinding.ActivityOpenNoteBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OpenNote : AppCompatActivity() {

    private var binding: ActivityOpenNoteBinding? = null
    private var selectedNote: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpenNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        selectedNote = intent.getSerializableExtra("SelectedNote") as Note

        binding?.llBg?.setBackgroundColor(selectedNote!!.color!!)
        binding?.tvTitleOpen?.text = selectedNote?.title
        binding?.tvDesOpen?.text = selectedNote?.des

        // Back button functionality
        binding?.ivBackButton?.setOnClickListener {
            finish()
        }

        // Edit button click listener
        binding?.ivEditButton?.setOnClickListener {
            val editNoteIntent = Intent(this, EditNote::class.java)
            editNoteIntent.putExtra("EditSelectedNote", selectedNote)
            startActivity(editNoteIntent)
        }

        // Display due date
        binding?.tvDueDate?.text = selectedNote?.dueDate?.let {
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
        } ?: "No Due Date"
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
