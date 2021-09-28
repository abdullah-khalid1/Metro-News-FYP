package com.mynews.metronews.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.mynews.metronews.R
import kotlinx.android.synthetic.main.activity_api_news_detail.*

class ApiNewsDetail : AppCompatActivity() {
    private var readFullArticle: Button? = null
    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_api_news_detail)

        readFullArticle = findViewById(R.id.read_full_article_btn)


        val bundle: Bundle = intent.extras!!
        val title: String = bundle.getString("title").toString()
        val description = bundle.getString("description")
        val thumbnail = bundle.getString("thumbnail")
        val source = bundle.getString("source")
        url = bundle.getString("url")

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }



        Glide.with(this).load(thumbnail).centerCrop()
            .into(api_news_detail_image)

        api_news_detail_title.text = title
        api_news_detail_description.text = description
        api_news_detail_source.text = "Source: $source"

        readFullArticle!!.setOnClickListener {

            val intent = Intent(this, NewsWebView::class.java)
            intent.putExtra("url", url)
            startActivity(intent)
        }
    }

    private fun showWebViewDialog() {




    }
}
