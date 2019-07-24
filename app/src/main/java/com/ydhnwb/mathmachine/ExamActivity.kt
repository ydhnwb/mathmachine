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
import com.ydhnwb.mathmachine.adapters.QuestionAdapter
import com.ydhnwb.mathmachine.models.Question
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_exam.*
import kotlinx.android.synthetic.main.bottom_bar_submit.*
import kotlinx.android.synthetic.main.content_exam.*

class ExamActivity : AppCompatActivity() {

    private var questions = mutableListOf<Question>()
    companion object {
        public var answers = mutableMapOf<Question, String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()
        rv_examination.layoutManager = LinearLayoutManager(this@ExamActivity)
        loadData()
        btn_submit.setOnClickListener {
            for(s in answers){
                println("uwu "+s.value)
            }
            Toast.makeText(this, answers.size.toString(), Toast.LENGTH_LONG).show()
        }
    }

    private fun getExamKey() : String = intent.getStringExtra("EXAM_KEY")

    private fun loadData(){
        val r = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM)
        r.child(getExamKey()).child("questions").addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) { Toast.makeText(this@ExamActivity, "Tidak dapat memuat soal", Toast.LENGTH_LONG).show() }
            override fun onDataChange(p0: DataSnapshot) {
                questions.clear()
                if(p0.exists()){
                    for(i in p0.children){
                        val x = i.getValue(Question::class.java)
                        println("Uwu ${x!!.key}")
                        questions.add(i.getValue(Question::class.java)!!)
                    }
                }
                rv_examination.adapter = QuestionAdapter(questions, this@ExamActivity)
            }
        })
    }
}
