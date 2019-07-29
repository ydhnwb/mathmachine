package com.ydhnwb.mathmachine

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.adapters.ManageQuestionAdapter
import com.ydhnwb.mathmachine.adapters.QuestionAdapter
import com.ydhnwb.mathmachine.models.Question
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_detail_manage.*
import kotlinx.android.synthetic.main.content_detail_manage.*
import kotlinx.android.synthetic.main.content_exam.*

class DetailManageActivity : AppCompatActivity() {
    private var questions = mutableListOf<Question>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_manage)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        rv_list_quest.apply {
            layoutManager = LinearLayoutManager(this@DetailManageActivity)
        }
        loadData()
    }

    private fun loadData(){
        val r = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM)
        r.child(getExamKey()).child("questions").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) { Toast.makeText(this@DetailManageActivity, "Tidak dapat memuat soal", Toast.LENGTH_LONG).show() }
            override fun onDataChange(p0: DataSnapshot) {
                questions.clear()
                if(p0.exists()){
                    for(i in p0.children){
                        questions.add(i.getValue(Question::class.java)!!)
                    }
                }
                rv_list_quest.adapter = ManageQuestionAdapter(questions, this@DetailManageActivity, getExamKey())
            }
        })
    }

    private fun getExamKey() : String = intent.getStringExtra("EXAM_KEY")

}
