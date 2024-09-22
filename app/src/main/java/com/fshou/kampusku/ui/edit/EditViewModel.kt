package com.fshou.kampusku.ui.edit

import androidx.lifecycle.ViewModel
import com.fshou.kampusku.data.StudentRepository
import com.fshou.kampusku.data.database.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class EditViewModel(private val studentRepository: StudentRepository): ViewModel() {
    private var _student: MutableStateFlow<StudentUiState> = MutableStateFlow(
        StudentUiState.Idle())
    val student: StateFlow<StudentUiState> = _student

    fun loadStudent(no: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            studentRepository.getStudentByNo(no)
                .catch { _student.value = StudentUiState.Error("Error when loading Student") }
                .collect {
                    _student.value = StudentUiState.Success(it)
                }
        }
    }
    fun saveUpdatedStudent(student: Student) {
        CoroutineScope(Dispatchers.IO).launch {
            studentRepository.updateStudent(student)
        }
    }
}


sealed class StudentUiState {
    data class Success(val student: Student) : StudentUiState()
    class Idle : StudentUiState()
    data class Error(val msg: String) : StudentUiState()
}