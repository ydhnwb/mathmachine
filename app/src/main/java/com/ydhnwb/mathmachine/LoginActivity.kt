package com.ydhnwb.mathmachine

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.models.Student
import com.ydhnwb.mathmachine.utils.Constants


class LoginActivity : AppCompatActivity() {
    private var auth = FirebaseAuth.getInstance()
    private var authListener : FirebaseAuth.AuthStateListener? =  null
    private var sharedPref : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        sharedPref = getSharedPreferences("USER", MODE_PRIVATE)
        go_to_dosen.setOnClickListener { startActivity(Intent(this@LoginActivity, LecturerLoginActivity::class.java)) }
        doLogin()
    }

    private fun authCheck(b: Boolean){
        authListener = FirebaseAuth.AuthStateListener {
            if (it.currentUser != null){
                val i = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                }.also {
                    finish()
                }
                startActivity(i)
            }
        }
        if(b){
            Toast.makeText(this@LoginActivity, "User is logged in", Toast.LENGTH_LONG).show()
            auth.addAuthStateListener(authListener!!)
        }else{
            auth.removeAuthStateListener(authListener!!)
        }
    }


    private fun doLogin(){
        btn_login.setOnClickListener {
            val e = et_email.text.toString().trim()
            val p = et_password.text.toString().trim()
            if(!e.isEmpty() && !p.isEmpty()){
                if(p.length > 8){
                    val r = FirebaseDatabase.getInstance().getReference(Constants.REF_STUDENTS)
                    r.orderByChild("email").equalTo(e).limitToFirst(1).addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onCancelled(p0: DatabaseError) {}
                        override fun onDataChange(p0: DataSnapshot) {
                            if(p0.exists()){
                                val ss = mutableListOf<Student>()
                                for(snapshot in p0.children){
                                    ss.add(snapshot.getValue(Student::class.java)!!)
                                }
                                auth.fetchSignInMethodsForEmail(ss[0]!!.email!!).addOnCompleteListener{task ->
                                    if(task.result?.signInMethods?.size == 0){
                                        auth.createUserWithEmailAndPassword(e, p).addOnSuccessListener {
                                            startActivity(Intent(this@LoginActivity, MainActivity::class.java)).also {
                                                setLoggedIn(false, ss[0].key!!)
                                                finish()
                                            }
                                        }.addOnFailureListener {
                                            Toast.makeText(this@LoginActivity, "Tidak dapat membuat akun", Toast.LENGTH_LONG).show()
                                        }
                                    }else{
                                        auth.signInWithEmailAndPassword(e, p).addOnSuccessListener {
                                            startActivity(Intent(this@LoginActivity, MainActivity::class.java)).also {
                                                setLoggedIn(false, ss[0].key!!)
                                                finish()
                                            }
                                        }.addOnFailureListener {
                                            Toast.makeText(this@LoginActivity, "Tidak dapat masuk. Periksa kata sandi anda", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }else{
                                Toast.makeText(this@LoginActivity, "Email yang anda masukkan tidak terdaftar", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }else{
                    Toast.makeText(this@LoginActivity, "Password setidaknya delapan karakter", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@LoginActivity, "Isi semua form", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        authCheck(true)
    }

    override fun onStop() {
        super.onStop()
        authCheck(false)
    }

    private fun setLoggedIn(b : Boolean, key : String){
        val editor = sharedPref?.edit()
        editor?.putBoolean("IS_LECTURER", b)
        editor?.putString("USER_KEY", key)
        editor?.commit()
    }
}
