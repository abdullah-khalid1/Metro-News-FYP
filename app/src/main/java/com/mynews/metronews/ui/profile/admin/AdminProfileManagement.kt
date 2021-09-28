package com.mynews.metronews.ui.profile.admin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mynews.metronews.R
import kotlinx.android.synthetic.main.activity_author_profile_management.*

class AdminProfileManagement : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_profile_management)

        all_authors_posts_button.setOnClickListener {

            val intent = Intent(this, AllAuthorPosts::class.java)
            startActivity(intent)
        }

        all_authors_videos.setOnClickListener {

            val intent = Intent(this, AllAuthorsVideos::class.java)
            startActivity(intent)
        }
    }
}