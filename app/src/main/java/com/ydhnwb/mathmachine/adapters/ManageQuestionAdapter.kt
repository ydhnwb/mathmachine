package com.ydhnwb.mathmachine.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ydhnwb.mathmachine.QuestionFlowActivity
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.models.Question
import kotlinx.android.synthetic.main.list_item_manage_question.view.*

class ManageQuestionAdapter (var questions : List<Question>, var context : Context, var exam_key : String) : RecyclerView.Adapter<ManageQuestionAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_manage_question, parent, false))
    }

    override fun getItemCount() = questions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(questions[position], context)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(question : Question, c : Context){
            itemView.list_button_edit.setOnClickListener {
                context.startActivity(Intent(c, QuestionFlowActivity::class.java).apply {
                    putExtra("EXAM_KEY", exam_key)
                    putExtra("QUESTION", question)
                })
            }
            Glide.with(c).load(question.image).into(itemView.list_image_question)
        }
    }
}