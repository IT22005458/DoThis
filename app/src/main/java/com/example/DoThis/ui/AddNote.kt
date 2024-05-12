package com.example.DoThis.ui

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.example.DoThis.R
import com.example.DoThis.data.Note
import com.example.DoThis.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var viewModel: NoteViewModel
    private var dueDateInMillis: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            addNote()
        }

        binding.btnPickDueDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            { _, year, month, day ->
                val calendar = Calendar.getInstance()
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)
                dueDateInMillis = calendar.timeInMillis
                updateDueDateUI()
            }, year, month, dayOfMonth)

        datePickerDialog.show()
    }

    private fun updateDueDateUI() {
        dueDateInMillis?.let {
            val formattedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(it))
            binding.tvDueDate.text = formattedDate
        }
    }

    private fun addNote() {
        val title = binding.etNoteTitle.text.toString()
        val description = binding.etNoteDes.text.toString()

        if (title.isNotEmpty() && description.isNotEmpty()) {
            val randomColor = resources.getIntArray(R.array.cardColors).random()

            viewModel.addNote(Note(0, title, description, randomColor, dueDateInMillis))

            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        } else {
            Snackbar.make(binding.root, "Please enter title and description", Snackbar.LENGTH_SHORT).show()
        }
    }
}