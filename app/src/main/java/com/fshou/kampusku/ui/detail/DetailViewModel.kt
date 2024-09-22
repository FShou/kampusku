package com.fshou.kampusku.ui.detail

import android.os.Build.VERSION_CODES.M
import androidx.lifecycle.ViewModel
import com.fshou.kampusku.data.StudentRepository
import com.fshou.kampusku.data.database.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val studentRepository: StudentRepository): ViewModel() {
    private var _student: MutableStateFlow<DetailStudentUiState> = MutableStateFlow(DetailStudentUiState.Idle())
    val student: StateFlow<DetailStudentUiState> = _student

    fun loadDetailStudent(no: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            studentRepository.getStudentByNo(no)
                .catch { _student.value = DetailStudentUiState.Error("Error when loading Student detail") }
                .collect {
                _student.value = DetailStudentUiState.Success(it)
            }
        }
    }

    fun deleteStudent(){
        CoroutineScope(Dispatchers.IO).launch {
            student.collect {
                when(it) {
                    is DetailStudentUiState.Error -> {}
                    is DetailStudentUiState.Idle -> {}
                    is DetailStudentUiState.Success -> {
                        studentRepository.deleteStudent(it.student)
                    }
                }
            }
        }

    }
}

sealed class DetailStudentUiState {
    data class Success(val student: Student) : DetailStudentUiState()
    class Idle : DetailStudentUiState()
    data class Error(val msg: String) : DetailStudentUiState()
}