package com.ydhnwb.mathmachine

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.adapters.SeeScoreAdapter
import com.ydhnwb.mathmachine.models.Exam
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_see_score.*
import kotlinx.android.synthetic.main.content_see_score.*

class SeeScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_score)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        loadData()
    }


    private fun loadData(){
        rv_see_score.layoutManager = LinearLayoutManager(this@SeeScoreActivity)
        val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM)
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val exams = mutableListOf<Exam>()
                    for(p in p0.children){
                        exams.add(p.getValue(Exam::class.java)!!)
                    }
                    rv_see_score.adapter = SeeScoreAdapter(exams, this@SeeScoreActivity)
                }
            }

        })
    }


}
