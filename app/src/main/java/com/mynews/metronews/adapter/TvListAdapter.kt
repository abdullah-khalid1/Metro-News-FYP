package com.mynews.metronews.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.mynews.metronews.R
import com.mynews.metronews.model.Tv
import de.hdodenhof.circleimageview.CircleImageView

class TvListAdapter(val tv: ArrayList<Tv>, val context: Context, val player: SimpleExoPlayer) :
    RecyclerView.Adapter<TvListAdapter.MyListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyListHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.live_tv_row_item, parent, false)

        return MyListHolder(view)
    }

    override fun getItemCount(): Int {
        return tv.size
    }

    override fun onBindViewHolder(holder: MyListHolder, position: Int) {
        holder.thumbnail.setImageResource(tv[position].thumbnail)

        holder.thumbnail.setOnClickListener {

            when (position) {

                0 -> {

                    buildMediaSource(
                        "https://firebasestorage.googleapis.com/v0/b/sample-da0ee.appspot.com/o/videos%2FWhatsApp%20Video%202020-07-19%20at%2010.28.34%20PM(3).mp4?alt=media&token=cade4a65-1379-462a-907a-27514c306590",
                        context
                    )
                }

                1 -> {

                    buildMediaSource(
                        "https://firebasestorage.googleapis.com/v0/b/sample-da0ee.appspot.com/o/videos%2FWhatsApp%20Video%202020-07-19%20at%2010.28.34%20PM(1).mp4?alt=media&token=e2507504-be81-4f59-a8e5-34ca8ddaa0eb"
                        ,
                        context
                    )
                }

                2 -> {
                    buildMediaSource(
                        "https://firebasestorage.googleapis.com/v0/b/sample-da0ee.appspot.com/o/videos%2FWhatsApp%20Video%202020-07-19%20at%2010.28.34%20PM(2).mp4?alt=media&token=21c0d75a-2b5b-4390-9416-c74421b997cc",
                        context
                    )
                }

                3 -> {
                    buildMediaSource(
                        "https://firebasestorage.googleapis.com/v0/b/sample-da0ee.appspot.com/o/videos%2FWhatsApp%20Video%202020-07-19%20at%2010.28.34%20PM.mp4?alt=media&token=ef912e01-7bcd-4f4d-8cff-8624dddf4902",
                        context
                    )
                }
            }


            // LiveTvFragment().videoViewVisibility(true)
        }
    }

    class MyListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val thumbnail = itemView.findViewById(R.id.live_tv_item_text) as CircleImageView
    }


    private fun buildMediaSource(url: String, context: Context) {
        val uri = Uri.parse(url).toString()
        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, "yourApplicationName")
        )
// This is the MediaSource representing the media to be played.
        val videoSource: MediaSource =
            ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(uri))
// Prepare the player with the source.
        player.prepare(videoSource)
        player.playWhenReady = true
    }


}