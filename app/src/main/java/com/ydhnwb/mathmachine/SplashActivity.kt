package com.ydhnwb.mathmachine

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.hide()
        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java).also { finish() })
        }, 2000L)
    }
}
