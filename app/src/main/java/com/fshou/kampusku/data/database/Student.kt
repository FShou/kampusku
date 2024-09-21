package com.fshou.kampusku.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Student (
    @PrimaryKey(autoGenerate = true)
    val no: Int? = null,

    val name: String,

    val gender: Boolean,

    val address: String
)