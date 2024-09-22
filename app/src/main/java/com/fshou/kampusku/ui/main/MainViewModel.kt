package com.fshou.kampusku.ui.main

import androidx.lifecycle.ViewModel
import com.fshou.kampusku.data.StudentRepository
import com.fshou.kampusku.data.database.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val studentRepository: StudentRepository) : ViewModel() {
    private val _listStudent = MutableStateFlow<StudentsUiState>(StudentsUiState.Idle())
    val listStudent: StateFlow<StudentsUiState> = _listStudent

    fun loadAllStudent() {
        CoroutineScope(Dispatchers.IO).launch {
            studentRepository.getAllStudent()
                .collect { students ->
                    _listStudent.value = StudentsUiState.Success(students)
                }
        }
    }
}

sealed class StudentsUiState {
    data class Success(val students: List<Student>) : StudentsUiState()
    class Idle : StudentsUiState()
    data class Error(val msg: String) : StudentsUiState()
}