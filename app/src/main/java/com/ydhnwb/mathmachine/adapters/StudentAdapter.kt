package com.ydhnwb.mathmachine.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.StudentFlowActivity
import com.ydhnwb.mathmachine.models.Student
import kotlinx.android.synthetic.main.list_item_student.view.*

class StudentAdapter(private var students : List<Student>, private var context : Context) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_student, parent, false))

    override fun getItemCount() = students.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)  = holder.binding(students[position], context)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(student: Student, context: Context){
            itemView.tv_student_name.text = student.name
            itemView.tv_student_email.text = student.email
            itemView.setOnClickListener {
                context.startActivity(Intent(context, StudentFlowActivity::class.java).apply {
                    putExtra("IS_NEW", false)
                    putExtra("STUDENT", student)
                })
            }
        }
    }
}