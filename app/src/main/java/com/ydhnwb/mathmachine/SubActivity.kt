package com.ydhnwb.mathmachine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.ydhnwb.mathmachine.models.Lesson
import com.ydhnwb.mathmachine.utils.Constants
import com.ydhnwb.mathmachine.viewholders.SubLessonViewHolder

import kotlinx.android.synthetic.main.activity_sub.*
import kotlinx.android.synthetic.main.content_sub.*

class SubActivity : AppCompatActivity() {

    private lateinit var adapter : FirebaseRecyclerAdapter<Lesson, SubLessonViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        rv_sub.layoutManager = LinearLayoutManager(this@SubActivity)
        val databaseReference = FirebaseDatabase.getInstance().getReference(Constants.REF_DAILY_LESSONS)
        val fo = FirebaseRecyclerOptions.Builder<Lesson>().setQuery(databaseReference.child(getIdLesson()).child("sub"), Lesson::class.java).build()
        adapter = object : FirebaseRecyclerAdapter<Lesson, SubLessonViewHolder>(fo){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SubLessonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_lesson, parent, false))
            override fun onBindViewHolder(p0: SubLessonViewHolder, p1: Int, p2: Lesson) {
                p0.binding(p2, this@SubActivity)
            }
        }
        rv_sub.adapter = adapter
        adapter.startListening()
    }

    private fun getIdLesson() = intent.getStringExtra("ID")

}
