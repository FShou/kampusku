package com.fshou.kampusku.ui.detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.fshou.kampusku.R
import com.fshou.kampusku.data.database.Student
import com.fshou.kampusku.databinding.ActivityDetailBinding
import com.fshou.kampusku.ui.ViewModelFactory
import com.fshou.kampusku.ui.edit.EditActivity
import com.fshou.kampusku.ui.edit.EditActivity.Companion.EXTRA_STUDENT_NO
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL_STUDENT_NO = "student-no"
    }

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelFactory.getInstance(this).create(DetailViewModel::class.java)
    }

    private val studentNo: Int by lazy { intent.getIntExtra(EXTRA_DETAIL_STUDENT_NO, -1) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnEdit.setOnClickListener {
            startActivity(
                Intent(this, EditActivity::class.java).putExtra(
                    EXTRA_STUDENT_NO,
                    studentNo
                )
            )
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteStudent()
            finish()
        }


        lifecycleScope.launch {
            viewModel.student.collect {
                when (it) {
                    is DetailStudentUiState.Error -> {}
                    is DetailStudentUiState.Idle -> {}
                    is DetailStudentUiState.Success -> {
                        showStudentDetail(it.student)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadDetailStudent(studentNo)
    }

    private fun showStudentDetail(student: Student) {
        binding.apply {
            tvNo.text = student.no.toString()
            tvName.text = student.name
            tvGender.text = if (student.gender) "Laki-laki" else "Perempuan"
            tvAddress.text = student.address
        }
    }
}