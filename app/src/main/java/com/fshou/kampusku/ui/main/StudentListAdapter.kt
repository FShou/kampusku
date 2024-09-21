package com.fshou.kampusku.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.fshou.kampusku.R
import com.fshou.kampusku.data.database.Student
import com.fshou.kampusku.databinding.StudentItemBinding
import com.fshou.kampusku.ui.detail.DetailActivity

class StudentListAdapter(private val listStudent: List<Student>,val onClick: onItemClick) :
    RecyclerView.Adapter<StudentListAdapter.StudentItemViewHolder>() {

    inner class StudentItemViewHolder(private val binding: StudentItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Student) {
            binding.tvName.text = data.name
            binding.tvGender.text = if (data.gender) "Laki-laki" else "Perempuan"
            binding.root.setOnClickListener {
                onClick(student = data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = StudentItemBinding.inflate(layoutInflater, parent, false)
        return StudentItemViewHolder(itemView)
    }

    override fun getItemCount(): Int = listStudent.size

    override fun onBindViewHolder(holder: StudentItemViewHolder, position: Int) {
        holder.bind(listStudent[position])
    }

    fun interface onItemClick {
        operator fun invoke(student: Student)
    }
}