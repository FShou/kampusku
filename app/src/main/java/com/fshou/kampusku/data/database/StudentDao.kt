package com.fshou.kampusku.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface StudentDao {
    @Query("SELECT * FROM student")
    fun getStudents(): List<Student>

    @Query("SELECT * FROM student WHERE `no` == :no LIMIT 1")
    fun getStudentByNo(no: Int): Student

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudent(student: Student)

    @Update
    suspend fun updateStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)
}