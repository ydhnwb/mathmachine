package com.ydhnwb.mathmachine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.LinearLayout
import com.ydhnwb.mathmachine.models.Lesson
import es.voghdev.pdfviewpager.library.RemotePDFViewPager
import es.voghdev.pdfviewpager.library.adapter.PDFPagerAdapter
import es.voghdev.pdfviewpager.library.remote.DownloadFile
import es.voghdev.pdfviewpager.library.util.FileUtil
import kotlinx.android.synthetic.main.activity_read.*
import java.lang.Exception

class ReadActivity : AppCompatActivity(), DownloadFile.Listener{

    private lateinit var remotePdf : RemotePDFViewPager
    private lateinit var adapter: PDFPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_read)
        supportActionBar?.hide()
        init()
    }

    private fun init(){
        val l : DownloadFile.Listener = this
        remotePdf = RemotePDFViewPager(this, getCurrentLesson().moduleUrl, l)
        //root_read.viewTreeObserver.addOnGlobalLayoutListener {

        //}
    }

    private fun getCurrentLesson() : Lesson = intent.getParcelableExtra("LESSON")

    override fun onSuccess(url: String?, destinationPath: String?) {
        adapter = PDFPagerAdapter(this, FileUtil.extractFileNameFromURL(url))
        remotePdf.adapter = adapter
        root_read.addView(remotePdf, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    override fun onFailure(e: Exception?) {}

    override fun onProgressUpdate(progress: Int, total: Int) {}

}
