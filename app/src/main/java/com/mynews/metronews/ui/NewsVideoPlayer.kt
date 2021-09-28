package com.mynews.metronews.ui

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.activity_news_video_player.*


class NewsVideoPlayer : AppCompatActivity() {

    private var player: SimpleExoPlayer? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.mynews.metronews.R.layout.activity_news_video_player)

//        player =
//            ExoPlayerFactory.newSimpleInstance(
//                this,
//                DefaultRenderersFactory(this),
//                DefaultTrackSelector(),
//                DefaultLoadControl())

        if (supportActionBar != null) {
            supportActionBar!!.hide()
        }

        player = SimpleExoPlayer.Builder(this).build()

        val bundle: Bundle = intent.extras!!
        val uri = Uri.parse(bundle.getString("url")).toString()


        simpleExoPlayerView.player = player

        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            this,
            Util.getUserAgent(this, "yourApplicationName")
        )
// This is the MediaSource representing the media to be played.
        val videoSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(uri))
// Prepare the player with the source.
        player!!.prepare(videoSource)
        player!!.playWhenReady = true
    }

    override fun onPause() {

//        Toast.makeText(applicationContext , "Pause", Toast.LENGTH_LONG).show()
        super.onPause()

        if (player != null) {
            pausePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()

        if (player != null) {
            startPlayer()
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        simpleExoPlayerView!!.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }


    private fun pausePlayer() {
        player!!.playWhenReady = false
        player!!.playbackState
    }

    private fun startPlayer() {
        player!!.playWhenReady = true
        player!!.playbackState
    }

}
