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
        //holder.setIsRecyclable(false)
        holder.binding(questions[position], context, position+1)
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun binding(q : Question, c : Context, pos : Int){
            if(q.choosedAnswer.equals("a")){
                itemView.btn_a.setIconResource(R.drawable.ic_done_white_24dp)
            }else if(q.choosedAnswer.equals("b")){
                itemView.btn_b.setIconResource(R.drawable.ic_done_white_24dp)
            }else if(q.choosedAnswer.equals("c")){
                itemView.btn_c.setIconResource(R.drawable.ic_done_white_24dp)
            }else if(q.choosedAnswer.equals("d")){
                itemView.btn_d.setIconResource(R.drawable.ic_done_white_24dp)
            }else{
                reset()

            }

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
                    q.choosedAnswer = "a"
                    itemView.btn_a.setIconResource(R.drawable.ic_done_white_24dp)
                    ExamActivity.answers.put(q.key!!, "a")
                }
                itemView.btn_b.setOnClickListener {
                    reset()
                    q.choosedAnswer = "b"
                    ExamActivity.answers.put(q.key!!, "b")
                    itemView.btn_b.setIconResource(R.drawable.ic_done_white_24dp)
                }
                itemView.btn_c.setOnClickListener {
                    reset()
                    q.choosedAnswer = "c"
                    ExamActivity.answers.put(q.key!!, "c")
                    itemView.btn_c.setIconResource(R.drawable.ic_done_white_24dp)
                }
                itemView.btn_d.setOnClickListener {
                    reset()
                    q.choosedAnswer = "d"
                    ExamActivity.answers.put(q.key!!, "d")
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