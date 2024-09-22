package com.fshou.kampusku.ui.add

import androidx.lifecycle.ViewModel
import com.fshou.kampusku.data.StudentRepository
import com.fshou.kampusku.data.database.Student
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddStudentViewModel(private val studentRepository: StudentRepository) : ViewModel(){

     fun addNewStudent(student: Student) {
        CoroutineScope(Dispatchers.IO).launch {
            studentRepository.saveNewStudent(student)
        }
    }

}