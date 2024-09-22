package com.fshou.kampusku.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fshou.kampusku.data.StudentRepository
import com.fshou.kampusku.ui.add.AddStudentViewModel
import com.fshou.kampusku.ui.detail.DetailViewModel
import com.fshou.kampusku.ui.edit.EditViewModel
import com.fshou.kampusku.ui.main.MainViewModel

class ViewModelFactory private constructor(private val studentRepository: StudentRepository): ViewModelProvider.Factory{

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    StudentRepository.getInstance(context)
                )
            }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(studentRepository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(studentRepository) as T
            }
            modelClass.isAssignableFrom(AddStudentViewModel::class.java) -> {
                AddStudentViewModel(studentRepository) as T
            } modelClass.isAssignableFrom(EditViewModel::class.java) -> {
                EditViewModel(studentRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}