package com.fshou.kampusku.ui.add

import androidx.lifecycle.ViewModel
import com.fshou.kampusku.data.StudentRepository
import com.fshou.kampusku.data.database.Student

class AddStudentViewModel(private val studentRepository: StudentRepository) : ViewModel(){

    suspend fun addNewStudent(student: Student) {
        studentRepository.saveNewStudent(student)
    }

}