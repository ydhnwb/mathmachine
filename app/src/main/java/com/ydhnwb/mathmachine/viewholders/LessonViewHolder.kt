package com.ydhnwb.mathmachine.viewholders

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ydhnwb.mathmachine.SubActivity
import com.ydhnwb.mathmachine.models.MainLesson
import kotlinx.android.synthetic.main.list_item_lesson.view.*

class LessonViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
    fun binding(lesson : MainLesson, context : Context){
        itemView.list_name.text = lesson.name
        itemView.list_desc.text = lesson.description
        itemView.setOnClickListener {
            context.startActivity(Intent(context, SubActivity::class.java).apply {
                putExtra("ID", lesson.key)
            })
        }
    }
}