package com.ydhnwb.mathmachine

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.models.Lecturer
import com.ydhnwb.mathmachine.utils.Constants
import kotlinx.android.synthetic.main.activity_lecturer_login.*
import kotlinx.android.synthetic.main.content_lecturer_login.*

class LecturerLoginActivity : AppCompatActivity() {
    private var sharedPref : SharedPreferences? = null
    private val r = FirebaseDatabase.getInstance().getReference(Constants.REF_LECTURERS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecturer_login)
        setSupportActionBar(toolbar)
        supportActionBar?.hide()
        doLogin()
        sharedPref = getSharedPreferences("USER", MODE_PRIVATE)
        back.setOnClickListener { finish() }
    }

    private fun doLogin(){
        btn_login_dosen.setOnClickListener {
            val email = et_email.text.toString().trim()
            val pass = et_password.text.toString().trim()
            if(!email.isEmpty() && !pass.isEmpty()){
                if(pass.length > 8){
                    val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_LECTURERS)
                    ref.orderByChild("email").equalTo(email).limitToFirst(1).addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            if (p0.exists()){
                                val ls = mutableListOf<Lecturer>()
                                for (snapshot in p0.children){
                                    ls.add(snapshot.getValue(Lecturer::class.java)!!)
                                }
                                //val l = p0.getValue(Lecturer::class.java)
                                val auth = FirebaseAuth.getInstance()
                                auth.fetchSignInMethodsForEmail(ls[0].email!!).addOnCompleteListener{task ->
                                    if(task.result?.signInMethods?.size == 0){
                                        auth.createUserWithEmailAndPassword(email, pass).addOnSuccessListener {
                                            setLoggedIn(true, ls[0].key!!)
                                            finish()
                                        }.addOnFailureListener {
                                            Toast.makeText(this@LecturerLoginActivity, "Tidak dapat membuat akun", Toast.LENGTH_LONG).show()
                                        }
                                    }else{
                                        auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener {
                                            Toast.makeText(this@LecturerLoginActivity, "Login sukses", Toast.LENGTH_LONG).show()
                                            setLoggedIn(true, ls[0].key!!)
                                            finish()
                                        }.addOnFailureListener {
                                            Toast.makeText(this@LecturerLoginActivity, "Login gagal", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(this@LecturerLoginActivity, "Dosen dengan email $email tidak ditemukan", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }else{
                    Toast.makeText(this@LecturerLoginActivity, "Password minimal 8 karakter", Toast.LENGTH_LONG).show()
                }
            }else{
                Toast.makeText(this@LecturerLoginActivity, "Isi semua form", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setLoggedIn(b : Boolean, key : String){
        val editor = sharedPref?.edit()
        editor?.putBoolean("IS_LECTURER", b)
        editor?.putString("USER_KEY", key)
        editor?.commit()
    }
}
