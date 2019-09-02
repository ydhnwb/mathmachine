package com.ydhnwb.mathmachine

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.adapters.QuestionAdapter
import com.ydhnwb.mathmachine.models.Question
import com.ydhnwb.mathmachine.models.Score
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_exam.*
import kotlinx.android.synthetic.main.bottom_bar_submit.*
import kotlinx.android.synthetic.main.content_exam.*

class ExamActivity : AppCompatActivity() {

    private var questions = mutableListOf<Question>()
    companion object {
        public var answers = mutableMapOf<String, String>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()
        rv_examination.layoutManager = LinearLayoutManager(this@ExamActivity)
        loadData()
        btn_submit.setOnClickListener {
            if(answers.size < questions.size){
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Jawab semua pertanyaan terlebih dahulu").setCancelable(false)
                    .setPositiveButton("PAHAM"){dialog, which ->
                        dialog.cancel()
                    }
                val alertt = builder.create()
                alertt.show()
            }else{
                var i = 0
                for(d in questions){
                    val correctAns = d.answer.toString()
                    val answer = answers.get(d.key).toString()
                    if(correctAns.equals(answer)){
                        i += 1
                    }
                }
                val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_SCORE).child(getExamKey())
                val key = getSharedPreferences("USER", Context.MODE_PRIVATE)!!.getString("USER_KEY","undefined")
                if(!key.equals("undefined")){
                    ref.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}

                        override fun onDataChange(p0: DataSnapshot) {
                            if(p0.exists()){
                                val s = p0.getValue(Score::class.java)
                                if(s != null){
                                    val existScore = s.score
                                    s.score = i*10
                                    if(s.life == 2){
                                        s.life = 1
                                    }else if(s.life == 1){
                                        s.life = 0
                                    }else{
                                        s.life = 0
                                    }
                                    if(s.score > existScore){
                                        p0.ref.setValue(s)
                                        startActivity(Intent(this@ExamActivity, ScoreActivity::class.java).apply {
                                            putExtra("SCORE", i*10)
                                            putExtra("EXIST_SCORE", s.score)
                                            putExtra("LIFE", s.life!!)
                                        }).also { finish() }
                                    }else{
                                        s.score = existScore
                                        p0.ref.setValue(s)
                                        startActivity(Intent(this@ExamActivity, ScoreActivity::class.java).apply {
                                            putExtra("SCORE", i*10)
                                            putExtra("EXIST_SCORE", existScore)
                                            putExtra("LIFE", s.life!!)
                                        }).also { finish() }
                                    }

                                }
                            }else{
                                val score = Score(key, i*10, 2)
                                p0.ref.setValue(score)
                                startActivity(Intent(this@ExamActivity, ScoreActivity::class.java).apply {
                                    putExtra("SCORE", i*10)
                                    putExtra("EXIST_SCORE", score.score)
                                    putExtra("LIFE", score.life!!)
                                }).also { finish() }
                            }
                        }
                    })
                }

            }
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
                questions.shuffle()
                rv_examination.adapter = QuestionAdapter(questions, this@ExamActivity)
            }
        })
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Jika anda keluar dari halaman ujian maka nilai anda dianggap 0. Apakah anda yakin ingin keluar?")
            .setPositiveButton("KELUAR"){dialog, which ->
                val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_SCORE).child(getExamKey())
                val key = getSharedPreferences("USER", Context.MODE_PRIVATE)!!.getString("USER_KEY","undefined")
                if(!key.equals("undefined")){
                    ref.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            if(p0.exists()){
                                val s = p0.getValue(Score::class.java)
                                if(s != null){
                                    val existScore = s.score
                                    s.score = 0
                                    if(s.life == 2){
                                        s.life = 1
                                    }else if(s.life == 1){
                                        s.life = 0
                                    }else{
                                        s.life = 0
                                    }
                                    if(s.score > existScore){
                                        p0.ref.setValue(s)
                                        startActivity(Intent(this@ExamActivity, ScoreActivity::class.java).apply {
                                            putExtra("SCORE", 0)
                                            putExtra("EXIST_SCORE", s.score)
                                            putExtra("LIFE", s.life!!)
                                        }).also { finish() }
                                    }else{
                                        s.score = existScore
                                        p0.ref.setValue(s)
                                        startActivity(Intent(this@ExamActivity, ScoreActivity::class.java).apply {
                                            putExtra("SCORE", 0)
                                            putExtra("EXIST_SCORE", existScore)
                                            putExtra("LIFE", s.life!!)
                                        }).also { finish() }
                                    }

                                }
                            }else{
                                val score = Score(key, 0, 2)
                                p0.ref.setValue(score)
                                startActivity(Intent(this@ExamActivity, ScoreActivity::class.java).apply {
                                    putExtra("SCORE", 0)
                                    putExtra("EXIST_SCORE", score.score)
                                    putExtra("LIFE", score.life!!)
                                }).also { finish() }
                            }
                        }
                    })
                }
            }.setNegativeButton("BATAL"){dialog, which -> dialog.cancel()  }
        val alert = builder.create()
        alert.show()
    }

}
