package com.fshou.kampusku.data.database

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomDatabase.Callback
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@Database(
    entities = [Student::class],
    version = 1
)
abstract class StudentDb : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {

        @Volatile
        private var INSTANCE: StudentDb? = null

        fun getInstance(context: Context): StudentDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StudentDb::class.java,
                    "task.db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            INSTANCE?.let { studentDb ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    for (student in fakeStudents){
                                        studentDb.studentDao().insertStudent(
                                            student
                                        )
                                    }
                                }

                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

private val fakeStudents = List(5) {
    Student(
        no = it,
        name = "Students Name $it",
        gender = (it % 2 == 0),
        address = "Student Address $it, Banjarmasin"
    )
}