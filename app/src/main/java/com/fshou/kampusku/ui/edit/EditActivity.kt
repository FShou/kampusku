package com.fshou.kampusku.ui.edit

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.fshou.kampusku.R
import com.fshou.kampusku.data.database.Student
import com.fshou.kampusku.databinding.ActivityEditBinding
import com.fshou.kampusku.ui.ViewModelFactory
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.coroutines.launch

class EditActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_STUDENT_NO = "no-student"
    }

    private val binding by lazy { ActivityEditBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelFactory.getInstance(this).create(EditViewModel::class.java)
    }
    private val studentNo by lazy { intent.getIntExtra(EXTRA_STUDENT_NO, -1) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSave.setOnClickListener {
            saveStudent()
        }
        lifecycleScope.launch {
            viewModel.student.collect {
                when(it) {
                    is StudentUiState.Error -> {}
                    is StudentUiState.Idle -> {}
                    is StudentUiState.Success -> {
                        showStudentData(it.student)
                    }
                }
            }
        }
    }

    private fun saveStudent() {
        val name = binding.edName.text.toString()
        val gender = binding.edGender.text.toString()
        val address = binding.edAddress.text.toString()
        val student = Student(
            no = studentNo,
            name = name,
            gender = gender == "Laki-laki",
            address = address
        )
        viewModel.saveUpdatedStudent(student)
        finish()
    }

    private fun showStudentData(student: Student) {
        binding.apply {
            binding.edName.setText(student.name)
            binding.edGender.setText(if(student.gender)"Laki-laki" else "Perempuan")
            (binding.edGender as MaterialAutoCompleteTextView).setSimpleItems(R.array.gender)
            binding.edAddress.setText(student.address)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadStudent(studentNo)
    }
}