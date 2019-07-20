package com.ydhnwb.mathmachine.viewholders

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ydhnwb.mathmachine.ReadActivity
import com.ydhnwb.mathmachine.models.Lesson
import kotlinx.android.synthetic.main.list_item_lesson.view.*

class SubLessonViewHolder(itemView : View): RecyclerView.ViewHolder(itemView) {
    fun binding(lesson : Lesson, context : Context){
        itemView.list_name.text = lesson.name
        itemView.list_desc.text = lesson.description
        itemView.setOnClickListener {
            val intent = Intent(context, ReadActivity::class.java)
            intent.putExtra("LESSON", lesson)
            context.startActivity(intent)
        }
    }
}