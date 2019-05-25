package com.ydhnwb.mathmachine.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.models.Lesson
import com.ydhnwb.mathmachine.utils.Constants
import com.ydhnwb.mathmachine.viewholders.LessonViewHolder
import kotlinx.android.synthetic.main.fragment_daily.view.*

class DailyFragment : Fragment() {
    private var dailyList : MutableList<Lesson> = mutableListOf()
    private lateinit var adapter : FirebaseRecyclerAdapter<Lesson, LessonViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_daily, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.daily_recyclerview.layoutManager = LinearLayoutManager(activity)
        val databaseReference = FirebaseDatabase.getInstance().getReference(Constants.REF_DAILY_LESSONS)
        val fo = FirebaseRecyclerOptions.Builder<Lesson>().setQuery(databaseReference, Lesson::class.java).build()
        adapter = object : FirebaseRecyclerAdapter<Lesson, LessonViewHolder>(fo){
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LessonViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_lesson, parent, false))
            override fun onBindViewHolder(p0: LessonViewHolder, p1: Int, p2: Lesson) {
                p0.binding(p2, activity!!)
            }
        }
        view.daily_recyclerview.adapter = adapter
        adapter.startListening()
    }
}