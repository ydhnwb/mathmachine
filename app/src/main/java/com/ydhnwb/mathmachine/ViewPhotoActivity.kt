package com.ydhnwb.mathmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_view_photo.*
import java.lang.Exception

class ViewPhotoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_photo)
        supportActionBar?.hide()
        try{
            Glide.with(this@ViewPhotoActivity).load(getPhotoUrl()).into(photo_view)
        }catch (e : Exception){
            println(e.message)
            Toast.makeText(this@ViewPhotoActivity, "Gagal memuat soal", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPhotoUrl() : String = intent.getStringExtra("PHOTO_URL")
}
