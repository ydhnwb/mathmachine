package com.ydhnwb.mathmachine.adapters

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ydhnwb.mathmachine.ExamActivity
import com.ydhnwb.mathmachine.R
import com.ydhnwb.mathmachine.models.Exam
import com.ydhnwb.mathmachine.models.Question
import com.ydhnwb.mathmachine.models.Score
import com.ydhnwb.mathmachine.utils.Constants
import kotlinx.android.synthetic.main.list_item_lesson.view.*

class ExamAdapter (var exams : List<Exam>, var context : Context, var isLecturer : Boolean) : RecyclerView.Adapter<ExamAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_lesson, parent, false))

    override fun getItemCount() = exams.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.binding(exams[position], context, isLecturer)

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun binding(e : Exam, c : Context, b : Boolean){
            itemView.list_name.text = e.name
            itemView.list_desc.text = e.description
            if(b){
                if (e.status){ itemView.list_status.text = "OPEN"
                }else{ itemView.list_status.text = "CLOSED" }
            }
            itemView.setOnClickListener {
                if(b){
                    if(e.status){
                        val builder = AlertDialog.Builder(c)
                        builder.setMessage("Apakah anda ingin menutup ujian ini?")
                            .setPositiveButton("TUTUP UJIAN"){dialog, which ->
                                val k = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM)
                                k.child(e.key!!).child("status").setValue(false)
                                Toast.makeText(c, "Ujian telah ditutup", Toast.LENGTH_LONG).show()
                                itemView.list_status.text = "CLOSED"
                                e.status = false
                            }
                        val alert = builder.create()
                        alert.show()
                    }else{
                        val builder = AlertDialog.Builder(c)
                        builder.setMessage("Apakah anda yakin ingin membuka ujian ini?")
                            .setPositiveButton("BUKA UJIAN"){dialog, which ->
                                val k = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM)
                                k.child(e.key!!).child("status").setValue(true)
                                Toast.makeText(c, "Ujian telah dibuka", Toast.LENGTH_LONG).show()
                                itemView.list_status.text = "OPEN"
                                e.status = true
                            }
                        val alert = builder.create()
                        alert.show()
                    }

                }else{
                    if(e.status){
                        val builder = AlertDialog.Builder(c)
                        val r = FirebaseDatabase.getInstance().getReference(Constants.REF_SCORE).child(e.key!!)
                        val key = c.getSharedPreferences("USER", Context.MODE_PRIVATE).getString("USER_KEY","undefined")
                        if (!key.equals("undefined")){
                            r.child(key).addListenerForSingleValueEvent(object : ValueEventListener{
                                override fun onCancelled(p0: DatabaseError) {}
                                override fun onDataChange(p0: DataSnapshot) {
                                    if(p0.exists()){
                                        val score = p0.getValue(Score::class.java)
                                        if(score != null){
                                            if(score.life == 0){
                                                builder.setMessage("Anda tidak dapat melakukan ujian lagi")
                                                    .setPositiveButton("PAHAM"){dialog, which ->
                                                        dialog.cancel()
                                                    }
                                                val alert = builder.create()
                                                alert.show()
                                            }else{
                                                builder.setMessage("Percobaan untuk melakukan ujian anda tinggal ${score.life} kali lagi. Lanjutkan?")
                                                    .setPositiveButton("MULAI UJIAN"){dialog, which ->
                                                        context.startActivity(Intent(c, ExamActivity::class.java).apply {
                                                            putExtra("EXAM_KEY", e.key)
                                                        })
                                                    }
                                                val alert = builder.create()
                                                alert.show()
                                            }
                                        }

                                    }else{
                                        builder.setMessage("Mohon untuk tetap pada halaman ujian dan tidak keluar dari aplikasi. Jika anda keluar dari aplikasi maka nilai anda tidak akan tersimpan")
                                            .setPositiveButton("MULAI UJIAN"){dialog, which ->
                                                context.startActivity(Intent(c, ExamActivity::class.java).apply {
                                                    putExtra("EXAM_KEY", e.key)
                                                })
                                            }
                                        val alert = builder.create()
                                        alert.show()
                                    }
                                }
                            })
                        }
                    }else{
                        val bz = AlertDialog.Builder(c)
                        bz.setMessage("Ujian belum dibuka oleh admin.")
                            .setPositiveButton("PAHAM"){ dialog, which -> dialog.cancel()  }
                        val a = bz.create()
                        a.show()
                    }
                }
            }
        }
    }
}