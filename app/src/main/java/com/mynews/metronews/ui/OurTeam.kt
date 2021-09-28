package com.mynews.metronews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mynews.metronews.R

class OurTeam : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_our_team)

        assert(
            supportActionBar != null //null check
        )

        supportActionBar!!.setDisplayHomeAsUpEnabled(true) //show back button
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}