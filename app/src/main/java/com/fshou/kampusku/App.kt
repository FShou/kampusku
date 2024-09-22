package com.fshou.kampusku

import android.app.Application
import com.fshou.kampusku.data.database.StudentDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            println(StudentDb.getInstance(this@App).studentDao().getStudents())
        }

    }
}