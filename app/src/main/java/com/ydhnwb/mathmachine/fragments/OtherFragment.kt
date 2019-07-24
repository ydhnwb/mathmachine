package com.ydhnwb.mathmachine.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.adapters.ExamAdapter
import com.ydhnwb.mathmachine.models.Exam
import com.ydhnwb.mathmachine.utils.Constants
import kotlinx.android.synthetic.main.fragment_other.view.*

class OtherFragment : Fragment() {
    private var ref = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM)
    private var exams = mutableListOf<Exam>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_other, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_exam.apply {
            layoutManager = LinearLayoutManager(activity)
        }
        loadData()
    }

    private fun loadData(){
        exams.clear()
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {}
            override fun onDataChange(p0: DataSnapshot) {
                if(p0.exists()){
                    for(i in p0.children){
                        exams.add(i.getValue(Exam::class.java)!!)
                    }
                }
                view?.rv_exam?.adapter = ExamAdapter(exams, activity!!)
            }
        })
    }

}