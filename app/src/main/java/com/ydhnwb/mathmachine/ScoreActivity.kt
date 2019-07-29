package com.ydhnwb.mathmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_score.*

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        supportActionBar?.hide()
        tv_score.text = getScore().toString()
        var s = ""
        if (getLife() > 0){
            s = "Anda bisa melakukan ujian lagi sebanyak ${getLife()} kali"
        }else{
            s = "Anda tidak bisa melakukan ujian lagi."
        }
        tv_info.text = "Nilai tertinggi anda adalah ${getExistScore()}. $s"
    }

    private fun getScore() = intent.getIntExtra("SCORE", 0)
    private fun getExistScore() = intent.getIntExtra("EXIST_SCORE", -1)
    private fun getLife() = intent.getIntExtra("LIFE", 0)
}
