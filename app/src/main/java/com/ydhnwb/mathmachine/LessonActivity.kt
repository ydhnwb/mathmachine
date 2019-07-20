package com.ydhnwb.mathmachine

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import androidx.appcompat.app.AppCompatActivity;
import com.ydhnwb.mathmachine.models.Lesson
import kotlinx.android.synthetic.main.activity_lesson.*
import kotlinx.android.synthetic.main.content_lesson.*

class LessonActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lesson)
        setSupportActionBar(toolbar)
        init()
        //initiate()
    }

    private fun initiate(){
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val req = DownloadManager.Request(Uri.parse(getCurrentLesson().moduleUrl))
        req.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
        req.setDestinationInExternalPublicDir(DIRECTORY_DOWNLOADS,"${getCurrentLesson().key}.pdf")
        downloadManager.enqueue(req)
    }

    private fun init(){
        pdf_view.fromUri(Uri.parse(getCurrentLesson().moduleUrl)).enableSwipe(true).swipeHorizontal(true).
                pageSnap(true).autoSpacing(true).pageFling(true).load()

    }

    private fun getCurrentLesson() : Lesson = intent.getParcelableExtra("LESSON")

}
