package com.ydhnwb.mathmachine.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.models.Score
import com.ydhnwb.mathmachine.models.Student
import com.ydhnwb.mathmachine.utils.Constants
import kotlinx.android.synthetic.main.list_item_score.view.*

class DetailSeeScoreAdapter(var scores : MutableList<Score>, var context : Context) : RecyclerView.Adapter<DetailSeeScoreAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_score, parent, false))

    override fun getItemCount() = scores.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(scores[position], context)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(score : Score, ctx: Context){
            val r = FirebaseDatabase.getInstance().getReference(Constants.REF_STUDENTS).child(score.key!!)
            r.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {}
                override fun onDataChange(p0: DataSnapshot) {
                    if(p0.exists()){
                        val student = p0.getValue(Student::class.java)
                        if(student != null){ itemView.list_score_name.text = student.name }
                    }else{ itemView.list_score_name.text = "Tidak tersedia" }
                }
            })
            itemView.list_score_score.text = score.score.toString()
        }
    }
}