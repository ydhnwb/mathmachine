package com.ydhnwb.mathmachine.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ydhnwb.mathmachine.DetailSeeScoreActivity
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.models.Exam
import kotlinx.android.synthetic.main.list_item_lesson.view.*

class SeeScoreAdapter (var exams : List<Exam>, var context : Context) : RecyclerView.Adapter<SeeScoreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_lesson, parent, false))
    }

    override fun getItemCount() = exams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(exams[position], context)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(exam: Exam, ctx : Context){
            itemView.list_name.text = exam.name
            itemView.setOnClickListener {
                ctx.startActivity(Intent(ctx, DetailSeeScoreActivity::class.java).apply {
                    putExtra("EXAM_KEY", exam.key)
                })
            }
            itemView.list_desc.text = "Cek nilai ${exam.name}"
        }
    }
}