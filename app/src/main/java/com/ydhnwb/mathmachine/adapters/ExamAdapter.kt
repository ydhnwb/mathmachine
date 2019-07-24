package com.ydhnwb.mathmachine.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ydhnwb.mathmachine.ExamActivity
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.models.Exam
import com.ydhnwb.mathmachine.models.Question
import kotlinx.android.synthetic.main.list_item_lesson.view.*

class ExamAdapter (var exams : List<Exam>, var context : Context) : RecyclerView.Adapter<ExamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_lesson, parent, false))

    override fun getItemCount() = exams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(exams[position], context)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(e : Exam, c : Context){
            itemView.list_name.text = e.name
            itemView.list_desc.text = e.description
            itemView.setOnClickListener {
                context.startActivity(Intent(c, ExamActivity::class.java).apply {
                    putExtra("EXAM_KEY", e.key)
                })
            }
        }
    }
}