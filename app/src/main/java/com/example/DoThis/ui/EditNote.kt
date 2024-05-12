package com.example.DoThis.ui

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.DoThis.data.Note
import com.example.DoThis.databinding.ActivityEditNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class EditNote : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private var binding: ActivityEditNoteBinding? = null
    private lateinit var viewModel: NoteViewModel
    private lateinit var selectedNote: Note
    private var dueDateInMillis: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        selectedNote = intent.getSerializableExtra("EditSelectedNote") as Note

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        // Back button functionality
        binding?.ivBackButton?.setOnClickListener {
            finish()
        }

        // Populate the UI with selected note data
        binding?.apply {
            etEditNoteTitle.setText(selectedNote.title ?: "")
            etEditNoteDes.setText(selectedNote.des ?: "")
            llEditBg.setBackgroundColor(selectedNote.color!!)
            tvDueDate.text = selectedNote.dueDate?.let {
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
            } ?: "No Due Date"
        }

        // Open date picker when due date button is clicked
        binding?.btnPickDueDate?.setOnClickListener {
            showDatePickerDialog()
        }

        // Save button click listener
        binding?.apply {
            btnSave.setOnClickListener {
                val editedNote = Note(
                    selectedNote.id,
                    etEditNoteTitle.text.toString().takeIf { it.isNotBlank() },
                    etEditNoteDes.text.toString().takeIf { it.isNotBlank() },
                    selectedNote.color,
                    dueDateInMillis
                )
                viewModel.updateNote(editedNote)
                Toast.makeText(this@EditNote, "Updated the note", Toast.LENGTH_SHORT).show()

                val mainIntent = Intent(this@EditNote, MainActivity::class.java)
                startActivity(mainIntent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this, this, year, month, dayOfMonth)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        dueDateInMillis = calendar.timeInMillis
        binding?.tvDueDate?.text = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(dueDateInMillis!!))
    }
}
