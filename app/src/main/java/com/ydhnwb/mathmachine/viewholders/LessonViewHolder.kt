package com.ydhnwb.mathmachine.viewholders

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ydhnwb.mathmachine.models.Lesson
import kotlinx.android.synthetic.main.list_item_lesson.view.*

class LessonViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
    fun binding(lesson : Lesson, context : Context){
        itemView.list_name.text = lesson.name
        itemView.list_desc.text = lesson.description
        itemView.setOnClickListener {
            Toast.makeText(context, "${lesson.key} tapped", Toast.LENGTH_SHORT).show()
        }
    }
}