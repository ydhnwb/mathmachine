package com.ydhnwb.mathmachine

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.adapters.DetailSeeScoreAdapter
import com.ydhnwb.mathmachine.models.Score
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_detail_see_score.*
import kotlinx.android.synthetic.main.content_detail_see_score.*
import kotlinx.android.synthetic.main.content_see_score.*

class DetailSeeScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_see_score)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        loadData()
    }

    private fun loadData(){
        rv_detail_see_score.layoutManager = LinearLayoutManager(this@DetailSeeScoreActivity)
        val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_SCORE).child(getExamKey())
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}

            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    val scores = mutableListOf<Score>()
                    for(s in p0.children){
                        scores.add(s.getValue(Score::class.java)!!)
                    }
                    rv_detail_see_score.adapter = DetailSeeScoreAdapter(scores, this@DetailSeeScoreActivity)
                }
            }
        })
    }

    private fun getExamKey() : String = intent.getStringExtra("EXAM_KEY")

}
