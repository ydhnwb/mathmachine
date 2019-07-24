package com.ydhnwb.mathmachine.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ydhnwb.mathmachine.ExamActivity
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.ViewPhotoActivity
import com.ydhnwb.mathmachine.models.Question
import kotlinx.android.synthetic.main.list_item_exam.view.*

class QuestionAdapter (private var questions : List<Question>, private var context : Context) : RecyclerView.Adapter<QuestionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_exam, parent, false))
    }

    override fun getItemCount() = questions.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        holder.binding(questions[position], context, position+1)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun binding(q : Question, c : Context, pos : Int){
            Glide.with(c).load(q.image).into(itemView.list_image)
            itemView.setOnClickListener {
                context.startActivity(Intent(context, ViewPhotoActivity::class.java).apply {
                    putExtra("PHOTO_URL", q.image)
                })
            }
            itemView.numbering.text = pos.toString()
            if(c is ExamActivity){
                itemView.btn_a.setOnClickListener {
                    reset()
                    itemView.btn_a.setIconResource(R.drawable.ic_done_white_24dp)
                    ExamActivity.answers.put(q, "a")
                    itemView.tv_your_ans.text = "Jawaban anda : A"
                }
                itemView.btn_b.setOnClickListener {
                    reset()
                    ExamActivity.answers.put(q, "b")
                    itemView.tv_your_ans.text = "Jawaban anda : B"
                    itemView.btn_b.setIconResource(R.drawable.ic_done_white_24dp)
                }
                itemView.btn_c.setOnClickListener {
                    reset()
                    ExamActivity.answers.put(q, "c")
                    itemView.tv_your_ans.text = "Jawaban anda : C"
                    itemView.btn_c.setIconResource(R.drawable.ic_done_white_24dp)
                }
                itemView.btn_d.setOnClickListener {
                    reset()
                    ExamActivity.answers.put(q, "a")
                    itemView.tv_your_ans.text = "Jawaban anda : D"
                    itemView.btn_d.setIconResource(R.drawable.ic_done_white_24dp)
                }

            }

        }

        fun reset(){
            itemView.btn_a.icon = null
            itemView.btn_b.icon = null
            itemView.btn_c.icon = null
            itemView.btn_d.icon = null
        }
    }

}