package com.fshou.kampusku.ui.add

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.fshou.kampusku.R
import com.fshou.kampusku.data.database.Student
import com.fshou.kampusku.databinding.ActivityAddStudentBinding
import com.fshou.kampusku.ui.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityAddStudentBinding.inflate(layoutInflater)
    }
    private val viewModel: AddStudentViewModel by lazy {
        ViewModelFactory.getInstance(this).create(AddStudentViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAdd.setOnClickListener {
            if (!checkInputValid()) {
                binding.addressLayout.error = "Some input might invalid"
                return@setOnClickListener
            }
            val student = Student(
                name = binding.edName.text.toString(),
                gender = (binding.edGender.text.toString() == "Laki-Laki"),
                address = binding.edAddress.text.toString()
            )
            lifecycleScope.launch {
                viewModel.addNewStudent(student)
            }
            finish()
        }

    }

    private fun checkInputValid(): Boolean {
        val name = binding.edName.text
        val gender = binding.edGender.text
        val address = binding.edAddress.text

        return !name.isNullOrEmpty() and !gender.isNullOrEmpty() and !address.isNullOrEmpty()
    }
}