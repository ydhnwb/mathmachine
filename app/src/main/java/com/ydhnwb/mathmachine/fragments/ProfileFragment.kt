package com.ydhnwb.mathmachine.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ydhnwb.mathmachine.ManageQuestionActivity
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.SeeScoreActivity
import com.ydhnwb.mathmachine.StudentActivity
import com.ydhnwb.mathmachine.models.Lecturer
import com.ydhnwb.mathmachine.models.Student
import com.ydhnwb.mathmachine.utils.Constants
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {
    private var auth = FirebaseAuth.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View = inflater.inflate(R.layout.fragment_profile, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(auth.currentUser != null){
            val key = auth.currentUser!!.uid
            var ref : DatabaseReference? = null
            if(activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)!!.getBoolean("IS_LECTURER", true)){
                ref = FirebaseDatabase.getInstance().getReference(Constants.REF_LECTURERS).child(key)
                manage_students.visibility = View.VISIBLE
                manage_exam.visibility = View.VISIBLE
                manage_score.visibility = View.VISIBLE
                manage_exam.setOnClickListener {
                    startActivity(Intent(activity, ManageQuestionActivity::class.java))
                }
                manage_students.setOnClickListener {
                    startActivity(Intent(activity, StudentActivity::class.java))
                }

                manage_score.setOnClickListener {
                    startActivity(Intent(activity, SeeScoreActivity::class.java))
                }
            }else{
                manage_exam.visibility = View.GONE
                manage_students.visibility = View.GONE
                manage_score.visibility = View.GONE
                ref = FirebaseDatabase.getInstance().getReference(Constants.REF_STUDENTS).child(key)
            }

            val k = activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)!!.getString("USER_KEY","undefined")
            k?.let {
                if(!k.equals("undefined")){
                    ref.child(k).addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            if(p0.exists()){
                                if(activity?.getSharedPreferences("USER", Context.MODE_PRIVATE)!!.getBoolean("IS_LECTURER", true)){
                                    val lecturer = p0.getValue(Lecturer::class.java)
                                    tv_name.text = lecturer?.name
                                    tv_email.text = lecturer?.email
                                }else{
                                    val student = p0.getValue(Student::class.java)
                                    tv_name.text = student?.name
                                    tv_email.text = student?.email
                                }
                            }
                        }
                    })
                }
            }

            view.logout.setOnClickListener {
                val settings = activity!!.getSharedPreferences("USER", MODE_PRIVATE);
                val editor = settings.edit();
                editor.clear();
                editor.commit();
                auth.signOut()
            }
        }
    }
}