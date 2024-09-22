package com.fshou.kampusku

import android.app.Application
import com.fshou.kampusku.data.database.StudentDb

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        StudentDb.getInstance(this)
    }
}