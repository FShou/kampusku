package com.fshou.kampusku.data

import android.content.Context
import com.fshou.kampusku.data.database.Student
import com.fshou.kampusku.data.database.StudentDao
import com.fshou.kampusku.data.database.StudentDb
import kotlinx.coroutines.flow.flow

class StudentRepository(
    private val studentDao: StudentDao
) {

    companion object {
        @Volatile
        private var instance: StudentRepository? = null

        fun getInstance(context: Context): StudentRepository {
            return instance ?: synchronized(this) {
                if (instance == null) {
                    val database = StudentDb.getInstance(context)
                    instance = StudentRepository(database.studentDao())
                }
                return instance as StudentRepository
            }

        }
    }

    fun getAllStudent() = flow {
        emit(studentDao.getStudents())
    }

    fun getStudentByNo(no: Int) = flow {
        emit(studentDao.getStudentByNo(no))
    }

    suspend fun saveNewStudent(student: Student) {
        studentDao.insertStudent(student)
    }

    suspend fun updateStudent(student: Student) {
        studentDao.updateStudent(student)
    }

    suspend fun deleteStudent(student: Student) {
        studentDao.deleteStudent(student)
    }

}
