package com.ydhnwb.mathmachine

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.adapters.StudentAdapter
import com.ydhnwb.mathmachine.models.Student
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_student.*
import kotlinx.android.synthetic.main.content_student.*

class StudentActivity : AppCompatActivity() {

    private var students = mutableListOf<Student>()
    private val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_STUDENTS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        rv_student.apply {
            layoutManager = LinearLayoutManager(this@StudentActivity)
        }
        fab.setOnClickListener { view ->
            startActivity(Intent(this@StudentActivity, StudentFlowActivity::class.java).apply {
                putExtra("IS_NEW", true)
            })
        }
    }

    private fun loadStudent(){
        students.clear()
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for (i in p0.children){
                        students.add(i.getValue(Student::class.java)!!)
                    }
                }
                rv_student.adapter = StudentAdapter(students, this@StudentActivity)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        loadStudent()
    }

}
