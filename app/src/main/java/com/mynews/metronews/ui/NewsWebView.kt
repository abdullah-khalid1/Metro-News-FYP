package com.mynews.metronews.ui

import android.os.Bundle
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.mynews.metronews.R
import kotlinx.android.synthetic.main.activity_news_web_view.*


class NewsWebView : AppCompatActivity() {

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_web_view)

        val bundle: Bundle = intent.extras!!
        url = bundle.getString("url")

        // Initializing Widgets
        val webView: WebView? = findViewById(R.id.web_view)

        webView!!.settings.javaScriptEnabled = true
        rlLoading.visibility= View.VISIBLE
        webView.visibility = View.INVISIBLE
        webView.loadUrl(url!!)

        webView.webChromeClient = object : WebChromeClient() {


            override fun onProgressChanged(view: WebView?, newProgress: Int) {

                if (newProgress == 100) {
                    webView.visibility = View.VISIBLE
                    rlLoading.visibility= View.INVISIBLE
                }
                super.onProgressChanged(view, newProgress)
            }

        }
    }
}