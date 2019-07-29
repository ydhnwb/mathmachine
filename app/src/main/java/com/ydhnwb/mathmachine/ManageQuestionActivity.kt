package com.ydhnwb.mathmachine

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.adapters.ManageExamAdapter
import com.ydhnwb.mathmachine.models.Exam
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_manage_question.*
import kotlinx.android.synthetic.main.content_manage_question.*

class ManageQuestionActivity : AppCompatActivity() {
    private var ref = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM)
    private var exams = mutableListOf<Exam>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_question)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        rv_manage_quest.apply {
            layoutManager = LinearLayoutManager(this@ManageQuestionActivity)
        }
        loadData()
    }

    private fun loadData(){
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for(i in p0.children){
                        exams.add(i.getValue(Exam::class.java)!!)
                    }
                }
                rv_manage_quest.adapter = ManageExamAdapter(exams, this@ManageQuestionActivity, true)
            }
        })
    }

}
