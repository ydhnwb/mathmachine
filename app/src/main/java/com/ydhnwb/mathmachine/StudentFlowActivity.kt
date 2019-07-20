package com.ydhnwb.mathmachine

import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.FirebaseDatabase
import com.ydhnwb.mathmachine.models.Student
import com.ydhnwb.mathmachine.utils.Constants

import kotlinx.android.synthetic.main.activity_student_flow.*
import kotlinx.android.synthetic.main.content_student_flow.*

class StudentFlowActivity : AppCompatActivity() {

    private val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_STUDENTS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_flow)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        fillForm()
        doSave()
    }

    private fun isNew() = intent.getBooleanExtra("ISNEW", true)

    private fun getCurrentStudent() = intent.getParcelableExtra<Student>("STUDENT")

    private fun fillForm(){
        if(!isNew()){
            et_email.setText(getCurrentStudent().email)
            et_name.setText(getCurrentStudent().name)
        }
    }

    private fun doSave(){
        fab.setOnClickListener { view ->
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            if(name.isNotEmpty() && email.isNotEmpty()){
                if(isNew()){
                    val s = Student()
                    s.email = email
                    s.name = name
                    s.name_lower = name.toLowerCase()
                    s.key = ref.push().key
                    s.key?.let {
                        ref.child(it).setValue(s)
                    }.also {
                        Toast.makeText(this@StudentFlowActivity, "Berhasil disimpan", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }else{
                    val s = Student()
                    s.email = email
                    s.name = name
                    s.name_lower = name.toLowerCase()
                    s.key = getCurrentStudent().key
                    s.key?.let {
                        ref.child(it).setValue(s)
                    }.also {
                        Toast.makeText(this@StudentFlowActivity, "Berhasil diperbarui", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }else{
                Toast.makeText(this, "Isi semua form yang diperlukan", Toast.LENGTH_LONG).show()
            }
        }
    }

}
