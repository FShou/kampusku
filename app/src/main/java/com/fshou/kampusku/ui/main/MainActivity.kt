package com.fshou.kampusku.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.fshou.kampusku.R
import com.fshou.kampusku.data.database.Student
import com.fshou.kampusku.databinding.ActivityMainBinding
import com.fshou.kampusku.ui.ViewModelFactory
import com.fshou.kampusku.ui.about.AboutActivity
import com.fshou.kampusku.ui.add.AddStudentActivity
import com.fshou.kampusku.ui.detail.DetailActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelFactory.getInstance(this).create(MainViewModel::class.java)
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

        binding.fab.setOnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    AddStudentActivity::class.java
                )
            )
        }

        lifecycleScope.launch {
            viewModel.listStudent
                .collect { uiState ->
                    when (uiState) {
                        is StudentsUiState.Error -> { println(uiState.msg) }
                        is StudentsUiState.Idle -> {}
                        is StudentsUiState.Success -> {
                            showStudentList(uiState.students)
                            println(uiState.students)
                        }
                    }
                }
        }


    }

    private fun showStudentList(students: List<Student>) {
        val rvLayout = LinearLayoutManager(this)
        val rvAdapter = StudentListAdapter(students) { student ->
            startActivity(Intent(this@MainActivity, DetailActivity::class.java))
        }
        binding.rvStudents.apply {
            layoutManager = rvLayout
            adapter = rvAdapter
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.loadAllStudent()
    }
}