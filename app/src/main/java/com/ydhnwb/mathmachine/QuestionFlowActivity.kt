package com.ydhnwb.mathmachine

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.ydhnwb.mathmachine.models.Question
import kotlinx.android.synthetic.main.activity_question_flow.*
import kotlinx.android.synthetic.main.content_question_flow.*
import android.app.Activity
import android.net.Uri
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.ydhnwb.mathmachine.utils.Constants
import java.io.File


class QuestionFlowActivity : AppCompatActivity() {
    private var question : Question? = Question()
    private var path : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_flow)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener { finish() }
        fill()
        chooseImage()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_save -> {
                val ref = FirebaseDatabase.getInstance().getReference(Constants.REF_EXAM).child(getParentQuestion()).child("questions")
                if (path.isNotEmpty()){
                    val sref = FirebaseStorage.getInstance().getReference(Constants.REF_SOAL_STORAGE + "_" + System.currentTimeMillis())
                    val f = File(path)
                    sref.putFile(Uri.fromFile(f)).addOnSuccessListener { t ->
                        sref.downloadUrl.addOnSuccessListener { d ->
                            Toast.makeText(this, "Succsessfully uploaded", Toast.LENGTH_SHORT).show()
                            question?.image = d.toString()
                            question?.answer = flow_sp_answer.selectedItem.toString()
                            ref.child(question?.key!!).setValue(question)
                            finish()
                        }
                    }.addOnFailureListener { fo ->
                        Toast.makeText(this@QuestionFlowActivity, "Tidak dapat mengupload " + fo.message, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this@QuestionFlowActivity, "Hanya mengupdate jawaban saja", Toast.LENGTH_LONG).show()
                    question?.answer = flow_sp_answer.selectedItem.toString()
                    ref.child(question?.key!!).setValue(question)
                    Toast.makeText(this@QuestionFlowActivity, "Berhasil ditambahkan", Toast.LENGTH_LONG).show()
                    finish()
                }
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getCurrentQuest() : Question = intent.getParcelableExtra("QUESTION")

    private fun fill(){
        question = getCurrentQuest()
        Glide.with(this@QuestionFlowActivity).load(question?.image).into(flow_preview_image)
        if(question?.answer.equals("a")){
            flow_sp_answer.setSelection(0)
        }else if(question?.answer.equals("b")){
            flow_sp_answer.setSelection(1)
        }else if(question?.answer.equals("c")){
            flow_sp_answer.setSelection(2)
        }else{
            flow_sp_answer.setSelection(3)
        }
    }

    private fun chooseImage() {
        flow_btn_pick.setOnClickListener {
            Pix.start(this@QuestionFlowActivity, Options.init().setRequestCode(9))
        }
    }

    private fun getParentQuestion() : String = intent.getStringExtra("EXAM_KEY")

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9 && resultCode == Activity.RESULT_OK && data != null) {
            val returnValue = data.getStringArrayListExtra(Pix.IMAGE_RESULTS)
            path = returnValue[0]
            Glide.with(this@QuestionFlowActivity).load(path).into(flow_preview_image)
        }
    }
}
